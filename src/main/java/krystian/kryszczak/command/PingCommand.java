package krystian.kryszczak.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public final class PingCommand extends Command {
    PingCommand() {
        super("ping");
    }

    @Override
    public Publisher<Void> execute(MessageCreateEvent event) {
        return event.getMessage().getChannel()
            .flatMap(channel -> channel.createMessage("Pong!"))
            .then();
    }
}
