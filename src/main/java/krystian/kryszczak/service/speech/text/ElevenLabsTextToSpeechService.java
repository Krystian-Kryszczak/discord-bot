package krystian.kryszczak.service.speech.text;

import io.micronaut.http.HttpResponse;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import krystian.kryszczak.http.elevenlabs.ElevenLabsRxHttpClient;
import krystian.kryszczak.model.elevenlabs.TextToSpeech;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class ElevenLabsTextToSpeechService implements TextToSpeechService {
    private final ElevenLabsRxHttpClient httpClient;
    private final TextToSpeech.Factory factory;

    public ElevenLabsTextToSpeechService(final ElevenLabsRxHttpClient httpClient, final TextToSpeech.Factory factory) {
        this.httpClient = httpClient;
        this.factory = factory;
    }

    @SneakyThrows
    @Override
    public Single<byte[]> textToSpeech(@NotNull String text) {
        return httpClient.textToSpeech(factory.createWithDefaults(text)).map(HttpResponse::body);
    }
}
