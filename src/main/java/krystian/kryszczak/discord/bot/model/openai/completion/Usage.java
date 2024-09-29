package krystian.kryszczak.discord.bot.model.openai.completion;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Usage(int completionTokens, int promptTokens, int totalTokens) {}
