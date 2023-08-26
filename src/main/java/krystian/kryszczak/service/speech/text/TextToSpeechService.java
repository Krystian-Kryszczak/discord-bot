package krystian.kryszczak.service.speech.text;

import io.reactivex.rxjava3.core.Single;
import org.jetbrains.annotations.NotNull;

public sealed interface TextToSpeechService permits ElevenLabsTextToSpeechService {
    Single<byte[]> textToSpeech(@NotNull String text);
}
