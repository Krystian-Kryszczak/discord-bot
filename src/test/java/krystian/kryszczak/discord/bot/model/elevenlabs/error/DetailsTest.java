package krystian.kryszczak.discord.bot.model.elevenlabs.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class DetailsTest {
    @Test
    public void detailsSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final String data = objectMapper.writeValueAsString(
                new Details(new Loc("test", 0), "Hello world!", "default")
            );

            final Details details = objectMapper.readValue(data, Details.class);

            assertNotNull(data);
            assertEquals(new Loc("test", 0), details.loc());
            assertEquals("Hello world!", details.msg());
            assertEquals("default", details.type());
            assertEquals(new Details(new Loc("test", 0), "Hello world!", "default"), details);
        });
    }
}
