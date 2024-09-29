package krystian.kryszczak.discord.bot.service.transcription;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.io.File;

public sealed interface TranscriptionService permits OpenAiTranscriptionService, WhisperTranscriptionService {
    Mono<String> createTranscription(final byte @NotNull [] wavAudioData);
    Mono<String> createTranscription(final @NotNull File wavFile);
}
