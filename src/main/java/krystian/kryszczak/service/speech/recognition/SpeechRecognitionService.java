package krystian.kryszczak.service.speech.recognition;

import io.reactivex.rxjava3.core.Single;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public sealed interface SpeechRecognitionService permits OpenAiSpeechRecognitionService {
    Single<String> recognizeSpeech(final @NotNull File file);
}
