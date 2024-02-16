package krystian.kryszczak.discord.bot.model.openai;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.Objects;

@Introspected
public record ChatCompletion(@NotNull String model, @NotNull Message @NotNull [] messages, boolean stream) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChatCompletion other) {
            return Objects.equals(this.model, other.model) &&
                Arrays.equals(this.messages, other.messages) &&
                this.stream == other.stream;
        }
        return false;
    }
}
