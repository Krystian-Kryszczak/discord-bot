package krystian.kryszczak.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import jakarta.inject.Singleton;
import krystian.kryszczak.model.audio.LavaPlayerAudioProvider;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public final class JoinCommand extends Command {
    private final LavaPlayerAudioProvider provider;
    JoinCommand(LavaPlayerAudioProvider provider) {
        super("join");
        this.provider = provider;
    }

    @Override
    public Publisher<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMember())
            .flatMap(Member::getVoiceState)
            .flatMap(VoiceState::getChannel)
            .flatMap(channel -> channel.join(spec -> spec.setProvider(provider)))
            .then();
    }
}
