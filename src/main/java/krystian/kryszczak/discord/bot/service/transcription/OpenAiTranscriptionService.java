package krystian.kryszczak.discord.bot.service.transcription;

import io.micronaut.http.MediaType;
import io.micronaut.http.client.multipart.MultipartBody;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.configuration.openai.OpenAiConfiguration;
import krystian.kryszczak.discord.bot.http.openai.ReactorOpenAIHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.File;

@Singleton
@RequiredArgsConstructor
public final class OpenAiTranscriptionService implements TranscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(OpenAiTranscriptionService.class);

    private final ReactorOpenAIHttpClient httpClient;
    private final OpenAiConfiguration configuration;

    @SneakyThrows
    @Override
    public @NotNull Mono<String> createTranscription(final byte @NotNull [] wavAudioData) {
        return httpClient.createTranscription(
                MultipartBody.builder()
                    .addPart("file", "audio.wav", MediaType.of("audio/wav"), wavAudioData)
                    .addPart("model", configuration.getTranscriptionModel())
                    .addPart("language", configuration.getLanguage())
                    .addPart("response_format", "text")
                    .build()
            ).doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete();
    }

    @SneakyThrows
    @Override
    public @NotNull Mono<String> createTranscription(final @NotNull File wavFile) {
        return httpClient.createTranscription(
                MultipartBody.builder()
                    .addPart("file", wavFile)
                    .addPart("model", configuration.getTranscriptionModel())
                    .addPart("language", configuration.getLanguage())
                    .addPart("response_format", "text")
                    .build()
            ).doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete();
    }
}
