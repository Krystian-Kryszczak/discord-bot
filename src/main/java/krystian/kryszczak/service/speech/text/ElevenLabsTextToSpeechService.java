package krystian.kryszczak.service.speech.text;

import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.elevenlabs.ElevenLabsConfiguration;
import krystian.kryszczak.http.elevenlabs.ElevenLabsRxHttpClient;
import krystian.kryszczak.model.elevenlabs.TextToSpeech;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

@Singleton
public final class ElevenLabsTextToSpeechService implements TextToSpeechService {
    private final ElevenLabsRxHttpClient httpClient;
    private final TextToSpeech.Factory factory;
    private final File savedFolder;

    public ElevenLabsTextToSpeechService(final ElevenLabsRxHttpClient httpClient, final TextToSpeech.Factory factory, final ElevenLabsConfiguration configuration) {
        this.httpClient = httpClient;
        this.factory = factory;
        this.savedFolder = new File(configuration.getDefaultSaveFolder());
        if (!this.savedFolder.isDirectory()) {
            this.savedFolder.mkdirs();
        }
    }

    @SneakyThrows
    @Override
    public Single<File> textToSpeechBufferedFile(@NotNull String text) {
        final File file = new File(savedFolder, text + ".mpeg");
        if (!file.exists() && file.createNewFile()) {
            final FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(
                Objects.requireNonNull(httpClient.textToSpeech(factory.createWithDefaults(text))
                    .blockingGet().body())
            );
            outputStream.close();
        }
        return Single.just(file);
    }
}