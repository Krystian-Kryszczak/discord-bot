package krystian.kryszczak.discord.bot.model.openai.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprob;
import krystian.kryszczak.discord.bot.model.openai.completion.log.TopLogprob;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class LogprobTest {
    @Test
    public void logprobSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final byte[] bytes = new byte[] { 1, 2, 3, 4 };
            final TopLogprob[] topLogprobs = new TopLogprob[] {
                new TopLogprob("TEST", 4f, new byte[] { 5, 6, 7, 8 })
            };
            final Logprob logprob = new Logprob("test", 4f, bytes, topLogprobs);

            final String data = objectMapper.writeValueAsString(logprob);
            final Logprob deserialized = objectMapper.readValue(data, Logprob.class);

            assertNotNull(deserialized);
            assertEquals("test", deserialized.token());
            assertEquals(4f, deserialized.logprob());
            assertArrayEquals(bytes, deserialized.bytes());
            assertArrayEquals(topLogprobs, deserialized.topLogprobs());
            assertEquals(logprob, deserialized);
        });
    }
}
