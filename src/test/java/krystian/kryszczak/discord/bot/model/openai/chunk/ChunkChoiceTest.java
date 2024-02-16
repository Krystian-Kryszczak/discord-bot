package krystian.kryszczak.discord.bot.model.openai.chunk;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.chunk.ChunkChoice;
import krystian.kryszczak.discord.bot.model.openai.completion.chunk.Delta;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprob;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprobs;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class ChunkChoiceTest {
    @Test
    public void choiceSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final Delta delta = new Delta("Hello world!", null, "user");
            final Logprobs logprobs = new Logprobs(new Logprob[] { new Logprob("test", 4f, null, null) });
            final ChunkChoice chunkChoice = new ChunkChoice(delta, logprobs, "stop", 0);

            final String data = objectMapper.writeValueAsString(chunkChoice);
            final ChunkChoice deserialized = objectMapper.readValue(data, ChunkChoice.class);

            assertNotNull(deserialized);
            assertEquals(delta, deserialized.delta());
            assertEquals(logprobs, deserialized.logprobs());
            assertEquals("stop", deserialized.finishReason());
            assertEquals(0, deserialized.index());
            assertEquals(chunkChoice, deserialized);
        });
    }
}
