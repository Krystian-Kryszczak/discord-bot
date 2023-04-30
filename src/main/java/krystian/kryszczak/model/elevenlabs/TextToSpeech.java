package krystian.kryszczak.model.elevenlabs;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.elevenlabs.ElevenLabsConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class TextToSpeech {
    private final @NotNull String text;
    private final @NotNull String modelId;
    private final @NotNull VoiceSettings voiceSettings;

    @Singleton
    @AllArgsConstructor
    public static final class Factory {
        private final ElevenLabsConfiguration configuration;

        public @NotNull TextToSpeech createWithDefaults(@NotNull String text) {
            return new TextToSpeech(text, configuration.getDefaultModelId(), configuration.getBuilder().build());
        }
    }
}
