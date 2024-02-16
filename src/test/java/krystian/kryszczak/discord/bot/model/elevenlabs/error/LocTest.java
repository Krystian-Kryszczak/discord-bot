package krystian.kryszczak.discord.bot.model.elevenlabs.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class LocTest {
    @Test
    public void locSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final String data = objectMapper.writeValueAsString(new Loc("test", 0));

            final Loc loc = objectMapper.readValue(data, Loc.class);

            assertNotNull(data);
            assertEquals("test", loc.text());
            assertEquals(0, loc.offset());
            assertEquals(new Loc("test", 0), loc);
        });
    }
}
