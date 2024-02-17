package krystian.kryszczak.discord.bot.model.elevenlabs.error;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;

@Serdeable
@Introspected
public record ElevenLabsError(@NotNull Details detail) {}
