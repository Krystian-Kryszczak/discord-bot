package krystian.kryszczak.factory.discord;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.discord.DiscordConfiguration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Objects;

@Factory
public final class DiscordFactory {

    @Singleton
    public JDA javaDiscordApi(final DiscordConfiguration discordConfiguration) {
        final String token = Objects.requireNonNull(discordConfiguration.getToken());

        return JDABuilder.createDefault(token, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES)
            .disableCache(CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS)
            .build();
    }
}
