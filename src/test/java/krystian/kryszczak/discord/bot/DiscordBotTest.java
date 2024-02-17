package krystian.kryszczak.discord.bot;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@MicronautTest
@RequiredArgsConstructor
public final class DiscordBotTest {
    private final EmbeddedApplication<?> application;

    @Test
    public void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }
}
