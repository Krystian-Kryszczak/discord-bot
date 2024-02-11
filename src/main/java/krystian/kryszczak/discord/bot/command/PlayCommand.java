package krystian.kryszczak.discord.bot.command;

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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Singleton
public final class PlayCommand extends Command {
    private static final Logger logger = LoggerFactory.getLogger(PlayCommand.class);
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
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        joinToUserVoiceChannel(event)
            .flatMap(str ->
                Mono.justOrEmpty(event.getOption(URL))
                    .map(OptionMapping::getAsString)
                    .doOnSuccess(botAudioProviderService::loadItem)
                    .map(url -> "I'm playing: " + url)
                    .doOnError(throwable -> logger.error("Error: {}", throwable.getMessage()))
                    .defaultIfEmpty("You must define valid youtube video url!")
                    .doOnSuccess(replay -> event.reply(replay).setEphemeral(true).queue()))
            .subscribe();
    }

    private @NotNull Mono<String> joinToUserVoiceChannel(final @NotNull SlashCommandInteractionEvent event) {
        return Mono.justOrEmpty(event.getGuild())
            .flatMap(guild -> {
                final Member member = event.getMember();
                if (member == null) {
                    return Mono.empty();
                }

                final GuildVoiceState voiceState = member.getVoiceState();
                if (voiceState == null) {
                    return Mono.empty();
                }

                final AudioChannelUnion channel = voiceState.getChannel();
                if (channel == null) {
                    return Mono.empty();
                }

                final var audioManager = guild.getAudioManager();

                audioManager.setSendingHandler(provider);
                audioManager.openAudioConnection(channel);

                return Mono.just(channel);
            })
            .map(Channel::getName)
            .map(channelName -> "I joined to \"" + channelName + "\" channel.")
            .doOnError(Throwable::printStackTrace)
            .doOnError(throwable -> logger.error("Error: {}", throwable.getMessage()))
            .defaultIfEmpty("You must be connected to voice channel!");
    }
}
