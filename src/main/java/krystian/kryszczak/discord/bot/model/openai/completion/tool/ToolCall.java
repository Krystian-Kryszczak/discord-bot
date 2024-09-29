package krystian.kryszczak.discord.bot.model.openai.completion.tool;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import krystian.kryszczak.discord.bot.model.openai.completion.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serdeable
@Introspected
public record ToolCall(@Nullable Integer index, @NotNull String id, @NotNull String type, @NotNull Function function) {}
