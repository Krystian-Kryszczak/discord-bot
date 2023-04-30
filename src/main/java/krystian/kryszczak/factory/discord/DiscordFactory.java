package krystian.kryszczak.factory.discord;

import discord4j.core.DiscordClient;
import io.micronaut.context.annotation.Factory;
import krystian.kryszczak.configuration.discord.DiscordConfiguration;
import lombok.AllArgsConstructor;

@Factory
@AllArgsConstructor
public final class DiscordFactory {
    private DiscordConfiguration discordConfiguration;

    public DiscordClient discordClient() {
        return DiscordClient.create(discordConfiguration.getToken());
    }
}
