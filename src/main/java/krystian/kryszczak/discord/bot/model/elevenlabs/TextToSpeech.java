package krystian.kryszczak.discord.bot.model.elevenlabs;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import krystian.kryszczak.discord.bot.configuration.elevenlabs.ElevenLabsConfiguration;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TextToSpeech(@NotNull String text, @NotNull String modelId, @NotNull VoiceSettings voiceSettings) {
    @Singleton
    @RequiredArgsConstructor
    public static final class Factory {
        private final ElevenLabsConfiguration configuration;

        @Contract("_ -> new")
        public @NotNull @org.jetbrains.annotations.NotNull TextToSpeech createWithDefaults(@NotNull String text) {
            return new TextToSpeech(text, configuration.getDefaultModelId(), configuration.getBuilder().build());
        }
    }
}
