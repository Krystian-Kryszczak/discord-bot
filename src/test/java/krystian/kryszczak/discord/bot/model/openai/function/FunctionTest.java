package krystian.kryszczak.discord.bot.model.openai.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.model.openai.completion.function.Function;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class FunctionTest {
    @Test
    public void functionSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final Function function = new Function("echo", "\"Hello world!\"");

            final String data = objectMapper.writeValueAsString(function);
            final Function deserialized = objectMapper.readValue(data, Function.class);

            assertNotNull(deserialized);
            assertEquals("echo", deserialized.name());
            assertEquals("\"Hello world!\"", deserialized.arguments());
            assertEquals(function, deserialized);
        });
    }
}
