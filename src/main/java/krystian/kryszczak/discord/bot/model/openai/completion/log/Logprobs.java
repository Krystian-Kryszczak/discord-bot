package krystian.kryszczak.discord.bot.model.openai.completion.log;

import io.micronaut.core.annotation.Introspected;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@Introspected
public record Logprobs(@Nullable Logprob[] content) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Logprobs other) {
            return Arrays.equals(this.content(), other.content());
        }
        return false;
    }
}
