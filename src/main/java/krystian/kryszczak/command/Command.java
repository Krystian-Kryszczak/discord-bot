package krystian.kryszczak.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class Command {
    @Getter
    private final String name;

    public static final Map<String, Command> commands = new HashMap<>();

    public abstract Publisher<Void> execute(MessageCreateEvent event);
}
