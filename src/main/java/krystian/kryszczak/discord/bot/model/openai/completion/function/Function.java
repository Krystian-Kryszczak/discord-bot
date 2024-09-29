package krystian.kryszczak.discord.bot.model.openai.completion.function;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serdeable
@Introspected
public record Function(@NotNull String name, @Nullable String arguments) {}
