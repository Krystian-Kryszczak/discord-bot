package krystian.kryszczak.discord.bot.model.openai.completion.log;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Arrays;
import java.util.Objects;

@Serdeable
@Introspected
public record TopLogprob(String token, float logprob, byte[] bytes) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TopLogprob other) {
            return Objects.equals(this.token(), other.token()) &&
                this.logprob() == other.logprob() &&
                Arrays.equals(this.bytes(), other.bytes());
        }
        return false;
    }
}
