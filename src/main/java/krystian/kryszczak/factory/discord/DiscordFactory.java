package krystian.kryszczak.factory.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.discord.DiscordConfiguration;

@Factory
public final class DiscordFactory {

    @Singleton
    public DiscordClient discordClient(final DiscordConfiguration discordConfiguration) {
        return DiscordClient.create(discordConfiguration.getToken());
    }

    @Singleton
    public GatewayDiscordClient gatewayDiscordClient(final DiscordClient discordClient) {
        return discordClient.login().block();
    }
}
