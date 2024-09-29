package krystian.kryszczak.discord.bot.service.speech.text;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

public sealed interface TextToSpeechService permits ElevenLabsTextToSpeechService {
    Flux<byte[]> textToSpeech(@NotNull String text);
    default Flux<byte[]> textToSpeech(@NotNull Flux<String> text) {
        return text.flatMap(this::textToSpeech);
    }
}
