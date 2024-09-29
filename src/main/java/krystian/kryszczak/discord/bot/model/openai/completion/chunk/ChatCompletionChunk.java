package krystian.kryszczak.discord.bot.model.openai.completion.chunk;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

@Serdeable
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatCompletionChunk(
    @NotNull String id,
    @NotNull ChunkChoice @NotNull [] chunkChoices,
    int created,
    @NotNull String model,
    @NotNull String systemFingerprint,
    @NotNull String object
) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChatCompletionChunk other) {
            return Objects.equals(this.id(), other.id()) &&
                Arrays.equals(this.chunkChoices(), other.chunkChoices()) &&
                this.created() == other.created() &&
                Objects.equals(this.model(), other.model()) &&
                Objects.equals(this.systemFingerprint(), other.systemFingerprint()) &&
                Objects.equals(this.object(), other.object());
        }
        return false;
    }
}
