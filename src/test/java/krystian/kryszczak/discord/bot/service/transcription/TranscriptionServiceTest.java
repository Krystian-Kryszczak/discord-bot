package krystian.kryszczak.discord.bot.service.transcription;

import io.micronaut.context.annotation.Primary;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import krystian.kryszczak.discord.bot.http.openai.ReactorOpenAIHttpClient;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@RequiredArgsConstructor
public final class TranscriptionServiceTest {
    private final TranscriptionService transcriptionService;

    @Test
    public void createTranscriptionReturnsExceptedValue() {
        final File file = new File("src/test/resources/voices/Hello world!.mp3");
        assertTrue(file.isFile());
        assertTrue(file.length() > 0);

        final String transcription = transcriptionService
            .createTranscription(file)
            .block();

        assertNotNull(transcription);
        assertFalse(transcription.isBlank());
        assertTrue(transcription.matches("(?i).*hello world.*"));
    }

    @Primary
    @MockBean
    public @NotNull ReactorOpenAIHttpClient httpClient() {
        final ReactorOpenAIHttpClient httpClient = mock(ReactorOpenAIHttpClient.class);

        when(httpClient.createTranscription(notNull()))
            .thenReturn(Mono.just("Hello world!"));

        return httpClient;
    }
}
