package krystian.kryszczak.discord.bot.model.elevenlabs.error;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotNull;

@Introspected
public record Loc(@NotNull String text, int offset) {}
