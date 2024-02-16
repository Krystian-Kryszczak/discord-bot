package krystian.kryszczak.discord.bot.model.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class ChatCompletionTest {
    @Test
    public void chatCompletionSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final String data = objectMapper.writeValueAsString(
                new ChatCompletion(
                    "default",
                    new Message[] {
                        new Message("user", "Hello world!")
                    },
                    false
                )
            );

            final ChatCompletion chatCompletion = objectMapper.readValue(data, ChatCompletion.class);

            assertNotNull(data);
            assertEquals("default", chatCompletion.model());
            assertArrayEquals(new Message[] { new Message("user", "Hello world!") }, chatCompletion.messages());
            assertFalse(chatCompletion.stream());
            assertEquals(
                new ChatCompletion(
                    "default",
                    new Message[] { new Message("user", "Hello world!") },
                    false
                ), chatCompletion);
        });
    }
}
