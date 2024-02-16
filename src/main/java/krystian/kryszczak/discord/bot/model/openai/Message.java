package krystian.kryszczak.discord.bot.model.openai;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotNull;

@Introspected
public record Message(@NotNull String role, @NotNull String content) {}
