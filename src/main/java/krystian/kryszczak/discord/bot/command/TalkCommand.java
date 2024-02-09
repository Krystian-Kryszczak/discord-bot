package krystian.kryszczak.discord.bot.command;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Optional;

@Singleton
public final class TalkCommand extends Command {
    private final AudioSendHandler provider;
    private final AudioReceiveHandler receiver;

    TalkCommand(final AudioSendHandler provider, final AudioReceiveHandler receiver) {
        super("talk", "Start a conversation with AiBot.", new OptionData[0]);
        this.provider = provider;
        this.receiver = receiver;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Maybe.fromOptional(Optional.ofNullable(event.getGuild()))
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
                audioManager.setReceivingHandler(receiver);
                audioManager.openAudioConnection(channel);

                return Maybe.just(channel);
            })
            .map(Channel::getName)
            .map(channelName -> "We will talk on the channel \"" + channelName + "\".")
            .doOnError(Throwable::printStackTrace)
            .onErrorReturn(throwable -> "Error: " + throwable.getMessage())
            .switchIfEmpty(Single.just("You must be connected to voice channel!"))
            .doAfterSuccess(it -> event.reply(it).setEphemeral(true).queue())
            .subscribe();
    }
}
