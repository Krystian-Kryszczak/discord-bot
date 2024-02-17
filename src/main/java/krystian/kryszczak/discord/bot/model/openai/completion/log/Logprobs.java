package krystian.kryszczak.discord.bot.model.openai.completion.log;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@Serdeable
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
