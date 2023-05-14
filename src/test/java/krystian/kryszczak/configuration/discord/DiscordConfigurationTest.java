package krystian.kryszczak.configuration.discord;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class DiscordConfigurationTest {
    @Test
    void testDiscordConfiguration() {
        ApplicationContext ctx = ApplicationContext.run();
        DiscordConfiguration discordConfiguration = ctx.getBean(DiscordConfiguration.class);
        final String token = discordConfiguration.getToken();

        assertNotNull(token);
        assertFalse(token.isBlank());
        assertEquals(72, token.length());
        ctx.close();
    }
}
