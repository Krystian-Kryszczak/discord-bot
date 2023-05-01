package krystian.kryszczak.listener.discord.message;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import krystian.kryszczak.command.Command;
import krystian.kryszczak.listener.discord.DiscordEventListener;
import org.reactivestreams.Publisher;

import java.util.Map;

@Singleton
public final class MessageCreateEventListener extends DiscordEventListener<MessageCreateEvent> {
    public MessageCreateEventListener(GatewayDiscordClient gatewayDiscordClient) {
        super(gatewayDiscordClient, MessageCreateEvent.class);
    }

    @Override
    public Publisher<?> onEventCall(final MessageCreateEvent event) {
        return Single.just(event.getMessage().getContent())
            .filter(message -> message.startsWith("!"))
            .map(it -> it.replace("!", ""))
            .flatMapPublisher(content ->
                Observable.fromIterable(Command.commands.entrySet())
                    .filter(entry -> entry.getKey().startsWith(content))
                    .firstElement().map(Map.Entry::getValue)
                    .flatMapPublisher(it -> it.execute(event))
            );
    }
}
