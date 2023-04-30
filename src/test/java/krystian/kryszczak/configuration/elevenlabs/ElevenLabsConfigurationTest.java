package krystian.kryszczak.configuration.elevenlabs;

import io.micronaut.context.ApplicationContext;
import krystian.kryszczak.model.elevenlabs.VoiceSettings;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ElevenLabsConfigurationTest {
    @Test
    void testElevenlabsConfiguration() {
        final Map<String, Object> items = new HashMap<>();
        items.put("eleven-labs.token", "test-token");
        items.put("eleven-labs.default-voice-id", "TxGEqnHWrfWFTfGW9XjX");
        items.put("eleven-labs.default-model-id", "eleven_monolingual_v1");
        items.put("eleven-labs.default-voice-settings.stability", 0.5f);
        items.put("eleven-labs.default-voice-settings.similarity-boost", 1.0f);

        ApplicationContext ctx = ApplicationContext.run(items);
        ElevenLabsConfiguration elevenlabsConfiguration = ctx.getBean(ElevenLabsConfiguration.class);
        VoiceSettings voiceSettings = elevenlabsConfiguration.builder.build();

        assertEquals("test-token", elevenlabsConfiguration.getToken());
        assertEquals("TxGEqnHWrfWFTfGW9XjX", elevenlabsConfiguration.getDefaultVoiceId());
        assertEquals("eleven_monolingual_v1", elevenlabsConfiguration.getDefaultModelId());
        assertEquals(0.5f, voiceSettings.getStability());
        assertEquals(1.0f, voiceSettings.getSimilarityBoost());

        ctx.close();
    }
}
