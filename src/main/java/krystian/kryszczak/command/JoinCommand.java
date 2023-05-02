package krystian.kryszczak.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.spec.VoiceChannelJoinSpec;
import discord4j.voice.AudioReceiver;
import jakarta.inject.Singleton;
import krystian.kryszczak.model.audio.provider.LavaPlayerAudioProvider;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public final class JoinCommand extends Command {
    private final LavaPlayerAudioProvider provider;
    private final AudioReceiver receiver;

    JoinCommand(final LavaPlayerAudioProvider provider, final AudioReceiver receiver) {
        super("join");
        this.provider = provider;
        this.receiver = receiver;
    }

    @Override
    public Publisher<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMember())
            .flatMap(Member::getVoiceState)
            .flatMap(VoiceState::getChannel)
            .flatMap(channel -> channel.join(VoiceChannelJoinSpec.builder().provider(provider).receiver(receiver).build()))
            .then();
    }
}
