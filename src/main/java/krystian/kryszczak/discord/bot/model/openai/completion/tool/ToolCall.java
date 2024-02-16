package krystian.kryszczak.discord.bot.model.openai.completion.tool;

import io.micronaut.core.annotation.Introspected;
import krystian.kryszczak.discord.bot.model.openai.completion.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Introspected
public record ToolCall(@Nullable Integer index, @NotNull String id, @NotNull String type, @NotNull Function function) {}
