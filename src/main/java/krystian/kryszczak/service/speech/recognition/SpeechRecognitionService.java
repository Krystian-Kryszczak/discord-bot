package krystian.kryszczak.service.speech.recognition;

import io.reactivex.rxjava3.core.Maybe;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public sealed interface SpeechRecognitionService permits OpenAiSpeechRecognitionService {
    Maybe<String> recognizeSpeech(final @NotNull File file);
    Maybe<String> recognizeSpeech(final byte @NotNull [] audioData);
}
