package krystian.kryszczak.configuration.discord;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class DiscordConfigurationTest {
    @Test
    void testDiscordConfiguration() {
        final Map<String, Object> items = new HashMap<>();
        items.put("discord.token", "test-token");
        items.put("discord.command-prefix", "!");
        items.put("discord.auto-remove-command", true);

        ApplicationContext ctx = ApplicationContext.run(items);
        DiscordConfiguration discordConfiguration = ctx.getBean(DiscordConfiguration.class);

        assertEquals("test-token", discordConfiguration.getToken());
        assertEquals("!", discordConfiguration.getCommandPrefix());
        assertTrue(discordConfiguration.isAutoRemoveCommand());

        ctx.close();
    }
}
