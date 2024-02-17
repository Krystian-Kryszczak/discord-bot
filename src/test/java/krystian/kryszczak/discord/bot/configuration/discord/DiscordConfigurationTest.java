package krystian.kryszczak.discord.bot.configuration.discord;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class DiscordConfigurationTest {
    @Test
    void discordConfiguration() {
        final Map<String, Object> items = new HashMap<>();
        items.put("discord.token", System.getenv("DISCORD_BOT_TOKEN"));
        items.put("discord.audio-receiver-await-time-millis", 4500);

        final ApplicationContext ctx = ApplicationContext.run(items);
        final DiscordConfiguration discordConfiguration = ctx.getBean(DiscordConfiguration.class);
        final String token = discordConfiguration.getToken();
        final long audioReceiverAwaitTimeMillis = discordConfiguration.getAudioReceiverAwaitTimeMillis();

        assertNotNull(token);
        assertFalse(token.isBlank());
        assertEquals(72, token.length());

        assertEquals(4500, audioReceiverAwaitTimeMillis);

        ctx.close();
    }
}
