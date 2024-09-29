package krystian.kryszczak.discord.bot.model.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.Choice;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprob;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprobs;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class ChunkChoiceTest {
    @Test
    public void choiceSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final Message message = Message.builder().role("user").content("Hello there, how may I assist you today?").build();
            final Logprobs logprobs = new Logprobs(new Logprob[] { new Logprob("test", 4f, null, null) });
            final Choice choice = new Choice("stop", 0, message, logprobs);

            final String data = objectMapper.writeValueAsString(choice);
            final Choice deserialized = objectMapper.readValue(data, Choice.class);

            assertNotNull(deserialized);
            assertEquals("stop", deserialized.finishReason());
            assertEquals(0, deserialized.index());
            assertEquals(message, deserialized.message());
            assertEquals(logprobs, deserialized.logprobs());
            assertEquals(choice, deserialized);
        });
    }
}
