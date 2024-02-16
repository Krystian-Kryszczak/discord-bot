package krystian.kryszczak.discord.bot.model.openai.completion.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import krystian.kryszczak.discord.bot.model.openai.completion.tool.ToolCall;
import lombok.Builder;

@Builder
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Message(
    @NotNull String role,
    @NotNull String content,
    @Nullable String name,
    @Nullable ToolCall[] toolCalls,
    @Nullable String toolCallId
) {}
