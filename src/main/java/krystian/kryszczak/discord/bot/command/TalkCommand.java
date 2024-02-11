package krystian.kryszczak.discord.bot.command;

import jakarta.inject.Singleton;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Singleton
public final class TalkCommand extends Command {
    private static final Logger logger = LoggerFactory.getLogger(TalkCommand.class);

    private final AudioSendHandler provider;
    private final AudioReceiveHandler receiver;

    TalkCommand(final AudioSendHandler provider, final AudioReceiveHandler receiver) {
        super("talk", "Start a conversation with AiBot.", new OptionData[0]);
        this.provider = provider;
        this.receiver = receiver;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        Mono.justOrEmpty(event.getGuild())
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
                audioManager.setReceivingHandler(receiver);
                audioManager.openAudioConnection(channel);

                return Mono.just(channel);
            })
            .map(Channel::getName)
            .map(channelName -> "We will talk on the channel \"" + channelName + "\".")
            .doOnError(Throwable::printStackTrace)
            .doOnError(throwable -> logger.error("Error: {}", throwable.getMessage()))
            .defaultIfEmpty("You must be connected to voice channel!")
            .doOnSuccess(it -> event.reply(it).setEphemeral(true).queue())
            .subscribe();
    }
}
