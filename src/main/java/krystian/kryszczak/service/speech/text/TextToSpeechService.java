package krystian.kryszczak.service.speech.text;

import io.reactivex.rxjava3.core.Single;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public sealed interface TextToSpeechService permits ElevenLabsTextToSpeechService {
    Single<File> textToSpeechBufferedFile(@NotNull String text);
}
