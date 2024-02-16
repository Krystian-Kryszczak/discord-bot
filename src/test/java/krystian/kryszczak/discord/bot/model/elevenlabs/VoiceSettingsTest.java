package krystian.kryszczak.discord.bot.model.elevenlabs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public final class VoiceSettingsTest {
    @Test
    public void voiceSettingsSerialization(@NotNull ObjectMapper objectMapper) {
        assertDoesNotThrow(() -> {
            final String data = objectMapper.writeValueAsString(new VoiceSettings(0.5f, 1.0f));

            final VoiceSettings voiceSettings = objectMapper.readValue(data, VoiceSettings.class);

            assertNotNull(data);
            assertEquals(0.5f, voiceSettings.stability());
            assertEquals(1.0f, voiceSettings.similarityBoost());
        });
    }
}
