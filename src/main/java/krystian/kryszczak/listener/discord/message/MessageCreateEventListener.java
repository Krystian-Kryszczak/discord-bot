package krystian.kryszczak.listener.discord.message;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import krystian.kryszczak.command.Command;
import krystian.kryszczak.configuration.discord.DiscordConfiguration;
import krystian.kryszczak.listener.discord.DiscordEventListener;
import org.reactivestreams.Publisher;

import java.util.Map;

@Singleton
public final class MessageCreateEventListener extends DiscordEventListener<MessageCreateEvent> {
    private final String commandPrefix;
    private final boolean autoRemoveCommand;

    public MessageCreateEventListener(final GatewayDiscordClient gatewayDiscordClient, final DiscordConfiguration configuration) {
        super(gatewayDiscordClient, MessageCreateEvent.class);
        this.commandPrefix = configuration.getCommandPrefix();
        this.autoRemoveCommand = configuration.isAutoRemoveCommand();
    }

    @Override
    public Publisher<?> onEventCall(final MessageCreateEvent event) {
        return Single.just(event.getMessage().getContent())
            .filter(message -> message.startsWith(commandPrefix))
            .map(it -> it.replace(commandPrefix, ""))
            .flatMapPublisher(content -> {
                if (autoRemoveCommand) {
                    event.getMessage().delete()
                        .subscribe();
                }
                return Observable.fromIterable(Command.commands.entrySet())
                    .filter(entry -> entry.getKey().startsWith(content.split(" ")[0]))
                    .firstElement().map(Map.Entry::getValue)
                    .flatMapPublisher(it -> it.execute(event));
            });
    }
}
