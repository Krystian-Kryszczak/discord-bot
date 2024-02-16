package krystian.kryszczak.discord.bot.model.openai.completion;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.Objects;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatCompletion(
    @NotNull String id,
    @NotNull Choice @NotNull [] choices,
    int created,
    @NotNull String model,
    @NotNull String systemFingerprint,
    @NotNull String object,
    @NotNull Usage usage
) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChatCompletion other) {
            return Objects.equals(this.id(), other.id()) &&
                Arrays.equals(this.choices(), other.choices()) &&
                this.created() == other.created() &&
                Objects.equals(this.model(), other.model()) &&
                Objects.equals(this.systemFingerprint(), other.systemFingerprint()) &&
                Objects.equals(this.object(), other.object()) &&
                Objects.equals(this.usage(), other.usage());
        }
        return false;
    }
}

