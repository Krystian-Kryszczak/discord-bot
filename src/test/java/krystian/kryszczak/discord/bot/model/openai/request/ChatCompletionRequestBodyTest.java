package krystian.kryszczak.discord.bot.model.openai.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;
import krystian.kryszczak.discord.bot.model.openai.completion.request.ChatCompletionRequestBody;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class ChatCompletionRequestBodyTest {
    @Test
    public void chatCompletionRequestBodySerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final Message message = Message.builder().role("user").content("Hi!").build();
            final Message[] messages = new Message[] { message };
            final ChatCompletionRequestBody requestBody = ChatCompletionRequestBody.builder()
                .messages(messages).model("gpt-3.5-turbo").build();

            final String data = objectMapper.writeValueAsString(requestBody);
            final ChatCompletionRequestBody deserialized = objectMapper.readValue(data, ChatCompletionRequestBody.class);

            assertNotNull(deserialized);
            assertArrayEquals(messages, deserialized.messages());
            assertEquals("gpt-3.5-turbo", deserialized.model());
            assertEquals(requestBody, deserialized);
        });
    }
}
