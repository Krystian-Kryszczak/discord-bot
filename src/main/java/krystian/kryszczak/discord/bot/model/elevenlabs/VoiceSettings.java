package krystian.kryszczak.discord.bot.model.elevenlabs;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class VoiceSettings {
    private final float stability;
    private final float similarityBoost;

    public static Builder builder() {
        return new Builder();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Builder {
        private float stability;
        private float similarityBoost;

        public VoiceSettings build() {
            return new VoiceSettings(stability, similarityBoost);
        }
    }
}
