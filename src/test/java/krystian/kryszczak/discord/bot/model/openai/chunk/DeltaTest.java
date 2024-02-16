package krystian.kryszczak.discord.bot.model.openai.chunk;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.chunk.Delta;
import krystian.kryszczak.discord.bot.model.openai.completion.function.Function;
import krystian.kryszczak.discord.bot.model.openai.completion.tool.ToolCall;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class DeltaTest {
    @Test
    public void deltaSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final ToolCall[] toolCalls = new ToolCall[] {
                new ToolCall(0, "test_1234", "function", new Function("echo", "Hi!"))
            };
            final Delta delta = new Delta("Hello world!", toolCalls, "user");

            final String data = objectMapper.writeValueAsString(delta);
            final Delta deserialized = objectMapper.readValue(data, Delta.class);

            assertNotNull(deserialized);
            assertEquals("Hello world!", deserialized.content());
            assertArrayEquals(toolCalls, deserialized.toolCalls());
            assertEquals("user", deserialized.role());
            assertEquals(delta, deserialized);
        });
    }
}
