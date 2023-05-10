package krystian.kryszczak.service.speech.recognition;

import io.micronaut.http.client.multipart.MultipartBody;
import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.openai.OpenAiConfiguration;
import krystian.kryszczak.http.openai.OpenAiRxHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@Singleton
@RequiredArgsConstructor
public final class OpenAiSpeechRecognitionService implements SpeechRecognitionService {
    private static final Logger logger = LoggerFactory.getLogger(OpenAiSpeechRecognitionService.class);

    private final OpenAiRxHttpClient httpClient;
    private final OpenAiConfiguration configuration;

    @SneakyThrows
    @Override
    public Maybe<String> recognizeSpeech(final @NotNull File file) {
        final byte[] bytes = new byte[(int) file.length()];
        try {
            new RandomAccessFile(file, "r").readFully(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return Maybe.empty();
        }
        return recognizeSpeech(bytes);
    }

    @Override
    public Maybe<String> recognizeSpeech(byte @NotNull [] audioData) {
        return httpClient.createTranscription(
            MultipartBody.builder()
                .addPart("file", "audio", audioData)
                .addPart("model", configuration.getAudioModel())
                .addPart("response_format", "text")
                .build()
        ).doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
        .onErrorComplete();
    }
}
