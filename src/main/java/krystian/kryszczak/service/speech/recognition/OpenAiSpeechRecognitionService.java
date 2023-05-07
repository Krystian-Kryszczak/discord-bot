package krystian.kryszczak.service.speech.recognition;

import io.micronaut.http.client.multipart.MultipartBody;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.openai.OpenAiConfiguration;
import krystian.kryszczak.http.openai.OpenAiRxHttpClient;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Singleton
@RequiredArgsConstructor
public final class OpenAiSpeechRecognitionService implements SpeechRecognitionService {
    private final OpenAiRxHttpClient httpClient;
    private final OpenAiConfiguration configuration;

    @Override
    public Single<String> recognizeSpeech(final @NotNull File file) {
        return httpClient.createTranscription(
            MultipartBody.builder()
                .addPart("file", file)
                .addPart("model", configuration.getAudioModel())
                .addPart("response_format", "text")
                .build()
        );
    }
}
