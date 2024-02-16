package krystian.kryszczak.discord.bot.model.openai.completion.chunk;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import krystian.kryszczak.discord.bot.model.openai.completion.tool.ToolCall;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Delta(@Nullable String content, @Nullable ToolCall[] toolCalls, @Nullable String role) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Delta other) {
            return Objects.equals(this.content(), other.content()) &&
                Arrays.equals(this.toolCalls(), other.toolCalls()) &&
                Objects.equals(this.role(), other.role());
        }
        return false;
    }
}
