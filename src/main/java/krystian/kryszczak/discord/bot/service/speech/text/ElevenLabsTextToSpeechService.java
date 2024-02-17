package krystian.kryszczak.discord.bot.service.speech.text;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.http.elevenlabs.ReactorElevenLabsHttpClient;
import krystian.kryszczak.discord.bot.model.elevenlabs.TextToSpeech;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

@Singleton
@RequiredArgsConstructor
public final class ElevenLabsTextToSpeechService implements TextToSpeechService {
    private final ReactorElevenLabsHttpClient httpClient;
    private final TextToSpeech.Factory factory;

    @Override
    public @NotNull Flux<byte[]> textToSpeech(@NotNull String text) {
        return httpClient.textToSpeech(factory.createWithDefaults(text)).map(HttpResponse::body).flux();
    }

    @SneakyThrows
    @Override
    public @NotNull Flux<byte[]> textToSpeech(@NotNull Flux<String> text) {
        return text.flatMap(this::textToSpeech);
    }
}
