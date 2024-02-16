package krystian.kryszczak.discord.bot.model.openai.completion.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import krystian.kryszczak.discord.bot.model.openai.completion.tool.ToolCall;
import lombok.Builder;

import java.util.Arrays;
import java.util.Objects;

@Builder
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Message(
    @NotNull String role,
    @NotNull String content,
    @Nullable String name,
    @Nullable ToolCall[] toolCalls,
    @Nullable String toolCallId
) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message other) {
            return Objects.equals(this.role(), other.role()) &&
                Objects.equals(this.content(), other.content()) &&
                Objects.equals(this.name(), other.name()) &&
                Arrays.equals(this.toolCalls(), other.toolCalls()) &&
                Objects.equals(this.toolCallId(), other.toolCallId());
        }
        return false;
    }
}
