package krystian.kryszczak.discord.bot.model.openai.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.log.TopLogprob;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class TopLogprobTest {
    @Test
    public void topLogprobsSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final byte[] bytes = new byte[] { 1, 2, 3, 4 };
            final TopLogprob topLogprob = new TopLogprob("TEST", 4f, bytes);

            final String data = objectMapper.writeValueAsString(topLogprob);
            final TopLogprob deserialized = objectMapper.readValue(data, TopLogprob.class);

            assertNotNull(deserialized);
            assertEquals("TEST", deserialized.token());
            assertEquals(4f, deserialized.logprob());
            assertArrayEquals(bytes, deserialized.bytes());
            assertEquals(topLogprob, deserialized);
        });
    }
}
