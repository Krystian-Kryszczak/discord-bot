package krystian.kryszczak.discord.bot.model.openai.completion.function;

import io.micronaut.core.annotation.Introspected;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Introspected
public record Function(@NotNull String name, @Nullable String arguments) {}
