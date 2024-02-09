package krystian.kryszczak.discord.bot.model.elevenlabs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public final class TextToSpeechTest {

    @Test
    void testVoiceSettingsToJson(ObjectMapper objectMapper) throws JsonProcessingException {
        final String result = objectMapper.writeValueAsString(
            new TextToSpeech("Hello world!", "TxGEqnHWrfWFTfGW9XjX", new VoiceSettings(0, 0))
        );

        final TextToSpeech textToSpeech = objectMapper.readValue(result, TextToSpeech.class);

        assertNotNull(result);
        assertEquals("Hello world!", textToSpeech.getText());
        assertEquals("TxGEqnHWrfWFTfGW9XjX", textToSpeech.getModelId());
        assertEquals(new VoiceSettings(0, 0), textToSpeech.getVoiceSettings());
    }

    @Test
    void testVoiceSettingsToJsonEqualsPattern(ObjectMapper objectMapper) throws JsonProcessingException {
        final String result = objectMapper.writeValueAsString(
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
            """
            .replaceAll("\n", "")
            .replaceAll(" ", "");

        assertNotNull(result);
        assertEquals(pattern, result);
    }
}
