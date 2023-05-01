package krystian.kryszczak.listener.discord;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.reactivestreams.Publisher;

public abstract class DiscordEventListener<T extends Event> {
    private final GatewayDiscordClient gatewayDiscordClient;
    private final Class<T> clazz;

    protected DiscordEventListener(GatewayDiscordClient gatewayDiscordClient, Class<T> clazz) {
        this.gatewayDiscordClient = gatewayDiscordClient;
        this.clazz = clazz;
        activate();
    }

    private void activate() {
        gatewayDiscordClient.getEventDispatcher().on(clazz)
            .flatMap(this::onEventCall)
            .subscribe();
    }

    protected abstract Publisher<?> onEventCall(final T event);
}
