package krystian.kryszczak.discord.bot.model.openai.completion.log;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Arrays;
import java.util.Objects;

@Serdeable
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Logprob(String token, float logprob, byte[] bytes, TopLogprob[] topLogprobs) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Logprob other) {
            return Objects.equals(this.token(), other.token()) &&
                this.logprob() == other.logprob() &&
                Arrays.equals(this.bytes(), other.bytes());
        }
        return false;
    }
}
