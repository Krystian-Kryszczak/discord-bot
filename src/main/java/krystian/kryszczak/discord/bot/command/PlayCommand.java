package krystian.kryszczak.discord.bot.command;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.service.provider.BotAudioProviderService;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Optional;

@Singleton
public final class PlayCommand extends Command {
    private static final String URL = "url";

    private final BotAudioProviderService botAudioProviderService;
    private final AudioSendHandler provider;

    PlayCommand(final BotAudioProviderService botAudioProviderService, AudioSendHandler provider) {
        super("play", "Play music from YouTube.", new OptionData[] {
            new OptionData(OptionType.STRING, URL, "Url to YouTube video.").setRequired(true)
        });
        this.botAudioProviderService = botAudioProviderService;
        this.provider = provider;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        joinToUserVoiceChannel(event)
            .flatMap(str ->
                Maybe.fromOptional(Optional.ofNullable(event.getOption(URL)))
                    .map(OptionMapping::getAsString)
                    .doAfterSuccess(botAudioProviderService::loadItem)
                    .map(url -> "I'm playing: " + url)
                    .onErrorReturn(throwable -> "Error: " + throwable.getMessage())
                    .defaultIfEmpty("You must define valid youtube video url!")
                    .doAfterSuccess(replay -> event.reply(replay).setEphemeral(true).queue()))
            .subscribe();
    }

    private @NonNull Single<String> joinToUserVoiceChannel(final SlashCommandInteractionEvent event) {
        return Maybe.fromOptional(Optional.ofNullable(event.getGuild()))
            .flatMap(guild -> {
                final Member member = event.getMember();
                if (member == null) {
                    return Maybe.empty();
                }

                final GuildVoiceState voiceState = member.getVoiceState();
                if (voiceState == null) {
                    return Maybe.empty();
                }

                final AudioChannelUnion channel = voiceState.getChannel();
                if (channel == null) {
                    return Maybe.empty();
                }

                final var audioManager = guild.getAudioManager();

                audioManager.setSendingHandler(provider);
                audioManager.openAudioConnection(channel);

                return Maybe.just(channel);
            })
            .map(Channel::getName)
            .map(channelName -> "I joined to \"" + channelName + "\" channel.")
            .doOnError(Throwable::printStackTrace)
            .onErrorReturn(throwable -> "Error: " + throwable.getMessage())
            .switchIfEmpty(Single.just("You must be connected to voice channel!"));
    }
}
