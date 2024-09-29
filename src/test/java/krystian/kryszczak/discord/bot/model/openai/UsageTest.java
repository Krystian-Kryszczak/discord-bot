package krystian.kryszczak.discord.bot.model.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.Usage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class UsageTest {
    @Test
    public void usageSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final Usage usage = new Usage(9, 12, 21);

            final String data = objectMapper.writeValueAsString(usage);
            final Usage deserialized = objectMapper.readValue(data, Usage.class);

            assertNotNull(deserialized);
            assertEquals(9, deserialized.completionTokens());
            assertEquals(12, deserialized.promptTokens());
            assertEquals(21, deserialized.totalTokens());
            assertEquals(deserialized.totalTokens(), deserialized.completionTokens() + deserialized.promptTokens());
            assertEquals(usage, deserialized);
        });
    }
}
