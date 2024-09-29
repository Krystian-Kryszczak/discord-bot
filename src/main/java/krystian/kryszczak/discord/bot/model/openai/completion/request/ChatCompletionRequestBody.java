package krystian.kryszczak.discord.bot.model.openai.completion.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;
import lombok.Builder;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Builder
@Serdeable
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatCompletionRequestBody(
    @NotNull Message @NotNull [] messages,
    @NotNull String model,
    @Nullable Float frequencyPenalty,
    @Nullable Map<String, String> logitBias,
    @Nullable Boolean logprobs,
    @Nullable Integer topLogprobs,
    @Nullable Integer maxTokens,
    @Nullable Integer n,
    @Nullable Float presencePenalty,
    @Nullable Integer seed,
    @Nullable String[] stop,
    @Nullable Boolean stream,
    @Nullable Float temperature,
    @Nullable Float topP,
    @Nullable String user
) {
    @Override
    public @Nullable Float frequencyPenalty() {
        if (this.frequencyPenalty == null) return null;
        return Math.max(-2f, Math.min(2f, this.frequencyPenalty));
    }

    @Override
    public @Nullable Float temperature() {
        if (this.temperature == null) return null;
        return Math.max(0f, Math.min(2f, this.temperature));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChatCompletionRequestBody other) {
            return Arrays.equals(this.messages(), other.messages()) &&
                Objects.equals(this.model(), other.model()) &&
                Objects.equals(this.frequencyPenalty(), other.frequencyPenalty()) &&
                Objects.equals(this.logitBias(), other.logitBias()) &&
                Objects.equals(this.logprobs(), other.logprobs()) &&
                Objects.equals(this.topLogprobs(), other.topLogprobs()) &&
                Objects.equals(this.maxTokens(), other.maxTokens()) &&
                Objects.equals(this.n(), other.n()) &&
                Objects.equals(this.presencePenalty(), other.presencePenalty()) &&
                Objects.equals(this.seed(), other.seed()) &&
                Arrays.equals(this.stop(), other.stop()) &&
                Objects.equals(this.stream(), other.stream()) &&
                Objects.equals(this.temperature(), other.temperature()) &&
                Objects.equals(this.topP(), other.topP()) &&
                Objects.equals(this.user(), other.user());
        }
        return false;
    }
}
