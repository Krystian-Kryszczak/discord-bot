package krystian.kryszczak.discord.bot.model.openai.completion.chunk;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import krystian.kryszczak.discord.bot.model.openai.completion.log.Logprobs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serdeable
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChunkChoice(@NotNull Delta delta, @Nullable Logprobs logprobs, @Nullable String finishReason, int index) {}
