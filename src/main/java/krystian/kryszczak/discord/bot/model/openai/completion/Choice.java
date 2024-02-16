package krystian.kryszczak.discord.bot.model.openai.completion;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprobs;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Choice(@NotNull String finishReason, int index, @NotNull Message message, @Nullable Logprobs logprobs) {}
