package krystian.kryszczak.discord.bot.factory.discord;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.configuration.discord.DiscordConfiguration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Factory
public final class DiscordFactory {
    @Singleton
    public @NotNull JDA javaDiscordApi(final @NotNull DiscordConfiguration discordConfiguration) {
        final String token = Objects.requireNonNull(discordConfiguration.getToken());

        return JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT)
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .enableCache(CacheFlag.VOICE_STATE)
            .disableCache(CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS)
            .build();
    }
}
