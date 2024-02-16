package krystian.kryszczak.discord.bot.model.openai.chunk;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.chunk.ChatCompletionChunk;
import krystian.kryszczak.discord.bot.model.openai.completion.chunk.ChunkChoice;
import krystian.kryszczak.discord.bot.model.openai.completion.chunk.Delta;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprob;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprobs;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class ChatCompletionChunkTest {
    @Test
    public void chatCompletionChunkSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final ChunkChoice[] chunkChoices = new ChunkChoice[] {
                new ChunkChoice(
                    new Delta("Hello world!", null, "user"),
                    new Logprobs(
                        new Logprob[] {
                            new Logprob("test", 4f, new byte[] { 1, 2, 3, 4 }, null)
                        }
                    ),
                    "stop",
                    0
                )
            };
            final ChatCompletionChunk chatCompletionChunk = new ChatCompletionChunk(
                "test-123",
                chunkChoices,
                1694268190,
                "gpt-3.5-turbo-0613",
                "fp_44709d6fcb",
                "chat.completion.chunk"
            );

            final String data = objectMapper.writeValueAsString(chatCompletionChunk);
            final ChatCompletionChunk deserialized = objectMapper.readValue(data, ChatCompletionChunk.class);

            assertNotNull(deserialized);
            assertArrayEquals(chunkChoices, deserialized.chunkChoices());
            assertEquals(1694268190, deserialized.created());
            assertEquals("gpt-3.5-turbo-0613", deserialized.model());
            assertEquals("fp_44709d6fcb", deserialized.systemFingerprint());
            assertEquals("chat.completion.chunk", deserialized.object());
            assertEquals(chatCompletionChunk, deserialized);
        });
    }
}
