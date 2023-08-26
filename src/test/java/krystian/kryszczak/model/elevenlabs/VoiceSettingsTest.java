package krystian.kryszczak.model.elevenlabs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public final class VoiceSettingsTest {

    @Test
    void testVoiceSettingsToJson(ObjectMapper objectMapper) throws JsonProcessingException {
        final String result = objectMapper.writeValueAsString(new VoiceSettings(0.5f, 1.0f));

        final VoiceSettings voiceSettings = objectMapper.readValue(result, VoiceSettings.class);

        assertNotNull(result);
        assertEquals(0.5f, voiceSettings.getStability());
        assertEquals(1.0f, voiceSettings.getSimilarityBoost());
    }
}
