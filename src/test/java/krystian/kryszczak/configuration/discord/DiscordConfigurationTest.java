package krystian.kryszczak.configuration.discord;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class DiscordConfigurationTest {
    @Test
    void testDiscordConfiguration() {
        final Map<String, Object> items = new HashMap<>();
        items.put("discord.token", "test-token");
        items.put("discord.command-prefix", "!");

        ApplicationContext ctx = ApplicationContext.run(items);
        DiscordConfiguration discordConfiguration = ctx.getBean(DiscordConfiguration.class);

        assertEquals("test-token", discordConfiguration.getToken());
        assertEquals("!", discordConfiguration.getCommandPrefix());

        ctx.close();
    }
}
