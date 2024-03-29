package krystian.kryszczak.discord.bot.model.elevenlabs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public final class TextToSpeechTest {
    @Test
    public void textToSpeechSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final String data = objectMapper.writeValueAsString(
                new TextToSpeech("Hello world!", "TxGEqnHWrfWFTfGW9XjX", new VoiceSettings(0, 0))
            );

            final TextToSpeech textToSpeech = objectMapper.readValue(data, TextToSpeech.class);

            assertNotNull(data);
            assertEquals("Hello world!", textToSpeech.text());
            assertEquals("TxGEqnHWrfWFTfGW9XjX", textToSpeech.modelId());
            assertEquals(new VoiceSettings(0, 0), textToSpeech.voiceSettings());
        });
    }

    @Test
    public void textToSpeechSerializationReturnsExceptedPattern(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final String data = objectMapper.writeValueAsString(
                new TextToSpeech("string", "eleven_monolingual_v1", new VoiceSettings(0, 0))
            );

            final String pattern = """
            {
              "text": "string",
              "model_id": "eleven_monolingual_v1",
              "voice_settings": {
                "stability": 0.0,
                "similarity_boost": 0.0
              }
            }
            """.replaceAll("\n|\\s", "");

            assertNotNull(data);
            assertEquals(pattern, data);
        });
    }
}
