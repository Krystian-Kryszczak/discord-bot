package krystian.kryszczak.discord.bot.model.elevenlabs.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class ElevenLabsErrorTest {
    @Test
    public void elevenLabsErrorSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final String data = objectMapper.writeValueAsString(
                new ElevenLabsError(new Details(new Loc("test", 0), "Hello world!", "default"))
            );

            final ElevenLabsError error = objectMapper.readValue(data, ElevenLabsError.class);

            assertNotNull(data);
            assertEquals(new Details(new Loc("test", 0), "Hello world!", "default"), error.detail());
            assertEquals(
                new ElevenLabsError(new Details(new Loc("test", 0), "Hello world!", "default")),
                error
            );
        });
    }
}
