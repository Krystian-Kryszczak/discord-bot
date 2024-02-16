package krystian.kryszczak.discord.bot.model.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.ChatCompletion;
import krystian.kryszczak.discord.bot.model.openai.completion.Choice;
import krystian.kryszczak.discord.bot.model.openai.completion.Usage;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class ChatCompletionTest {
    @Test
    public void chatCompletionSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final Choice[] choices = new Choice[] {
                new Choice(
                    "stop",
                    0,
                    new Message(
                        "assistant",
                        "Hello there, how may I assist you today?",
                        null,
                        null,
                        null
                    ),
                    null
                )
            };
            final Usage usage = new Usage(9, 12, 21);
            final ChatCompletion chatCompletion = new ChatCompletion(
                "test-12345",
                choices,
                1677652288,
                "gpt-3.5-turbo-0613",
                "fp_44709d6fcb",
                "chat.completion",
                usage
            );

            final String data = objectMapper.writeValueAsString(chatCompletion);
            final ChatCompletion deserialized = objectMapper.readValue(data, ChatCompletion.class);

            assertNotNull(deserialized);
            assertEquals("test-12345", deserialized.id());
            assertArrayEquals(choices, deserialized.choices());
            assertEquals(1677652288, deserialized.created());
            assertEquals("gpt-3.5-turbo-0613", deserialized.model());
            assertEquals("fp_44709d6fcb", deserialized.systemFingerprint());
            assertEquals("chat.completion", deserialized.object());
            assertEquals(usage, deserialized.usage());
            assertEquals(chatCompletion, deserialized);
        });
    }
}
