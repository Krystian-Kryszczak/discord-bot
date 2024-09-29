package krystian.kryszczak.discord.bot.model.openai.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprob;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprobs;
import krystian.kryszczak.discord.bot.model.openai.completion.log.TopLogprob;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class LogprobsTest {
    @Test
    public void logprobsChunkSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final Logprob[] content = new Logprob[] {
                new Logprob("test", 4f, new byte[] { 1, 2, 3, 4 }, new TopLogprob[] {})
            };
            final Logprobs logprobs = new Logprobs(content);

            final String data = objectMapper.writeValueAsString(logprobs);
            final Logprobs deserialized = objectMapper.readValue(data, Logprobs.class);

            assertNotNull(deserialized);
            assertArrayEquals(content, deserialized.content());
            assertEquals(logprobs, deserialized);
        });
    }
}
