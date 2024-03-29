package krystian.kryszczak.discord.bot.configuration.elevenlabs;

import io.micronaut.context.ApplicationContext;
import krystian.kryszczak.discord.bot.model.elevenlabs.VoiceSettings;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ElevenLabsConfigurationTest {
    @Test
    void elevenLabsConfiguration() {
        final Map<String, Object> items = new HashMap<>();
        items.put("eleven-labs.token", "test-token");
        items.put("eleven-labs.default-voice-id", "TxGEqnHWrfWFTfGW9XjX");
        items.put("eleven-labs.default-model-id", "eleven_monolingual_v1");
        items.put("eleven-labs.default-voice-settings.stability", 0.5f);
        items.put("eleven-labs.default-voice-settings.similarity-boost", 1.0f);

        final ApplicationContext ctx = ApplicationContext.run(items);
        final ElevenLabsConfiguration elevenlabsConfiguration = ctx.getBean(ElevenLabsConfiguration.class);
        final VoiceSettings voiceSettings = elevenlabsConfiguration.builder.build();

        assertEquals("test-token", elevenlabsConfiguration.getToken());
        assertEquals("TxGEqnHWrfWFTfGW9XjX", elevenlabsConfiguration.getDefaultVoiceId());
        assertEquals("eleven_monolingual_v1", elevenlabsConfiguration.getDefaultModelId());
        assertEquals(0.5f, voiceSettings.stability());
        assertEquals(1.0f, voiceSettings.similarityBoost());

        ctx.close();
    }
}
