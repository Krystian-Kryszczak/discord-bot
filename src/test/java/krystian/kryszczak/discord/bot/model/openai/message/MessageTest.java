package krystian.kryszczak.discord.bot.model.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class MessageTest {
    @Test
    public void messageSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final String data = objectMapper.writeValueAsString(new Message("user", "Hello world!"));

            final Message message = objectMapper.readValue(data, Message.class);

            assertNotNull(data);
            assertEquals("user", message.role());
            assertEquals("Hello world!", message.content());
            assertEquals(new Message("user", "Hello world!"), message);
        });
    }
}
