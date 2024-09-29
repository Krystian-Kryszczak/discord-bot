package krystian.kryszczak.discord.bot.model.openai.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.function.Function;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;
import krystian.kryszczak.discord.bot.model.openai.completion.tool.ToolCall;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class MessageTest {
    @Test
    public void messageSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final ToolCall[] toolCalls = new ToolCall[] {
                new ToolCall(0, "test_12345", "function", new Function("echo", "Hi!"))
            };
            final Message message = new Message("user", "Hello world!", "test", toolCalls, "tool_call_id");

            final String data = objectMapper.writeValueAsString(message);
            final Message deserialized = objectMapper.readValue(data, Message.class);

            assertNotNull(deserialized);
            assertEquals("user", deserialized.role());
            assertEquals("Hello world!", deserialized.content());
            assertEquals("test", deserialized.name());
            assertArrayEquals(toolCalls, deserialized.toolCalls());
            assertEquals("tool_call_id", deserialized.toolCallId());
            assertEquals(message, deserialized);
        });
    }
}
