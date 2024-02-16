package krystian.kryszczak.discord.bot.model.openai.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.function.Function;
import krystian.kryszczak.discord.bot.model.openai.completion.tool.ToolCall;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class ToolCallTest {
    @Test
    public void toolCallSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final Function function = new Function("echo", "Hi!");
            final ToolCall toolCall = new ToolCall(0, "test_id", "function", function);

            final String data = objectMapper.writeValueAsString(toolCall);
            final ToolCall deserialized = objectMapper.readValue(data, ToolCall.class);

            assertNotNull(deserialized);
            assertEquals(0, deserialized.index());
            assertEquals("test_id", deserialized.id());
            assertEquals("function", deserialized.type());
            assertEquals(function, deserialized.function());
            assertEquals(toolCall, deserialized);
        });
    }
}
