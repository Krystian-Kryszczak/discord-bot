package krystian.kryszczak.discord.bot.model.elevenlabs;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record VoiceSettings(float stability, float similarityBoost) {
    @Contract(" -> new")
    public static @NotNull Builder builder() {
        return new Builder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Builder {
        private float stability = 0.5f;
        private float similarityBoost = 1.0f;

        public Builder setStability(float stability) {
            this.stability = stability;
            return this;
        }

        public Builder setSimilarityBoost(float similarityBoost) {
            this.similarityBoost = similarityBoost;
            return this;
        }

        @Contract(" -> new")
        public @NotNull VoiceSettings build() {
            return new VoiceSettings(stability, similarityBoost);
        }
    }
}
