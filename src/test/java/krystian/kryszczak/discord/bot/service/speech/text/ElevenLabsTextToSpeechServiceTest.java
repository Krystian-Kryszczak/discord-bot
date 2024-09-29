package krystian.kryszczak.discord.bot.service.speech.text;

import io.micronaut.context.annotation.Primary;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Named;
import krystian.kryszczak.discord.bot.http.elevenlabs.ReactorElevenLabsHttpClient;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@RequiredArgsConstructor
public final class ElevenLabsTextToSpeechServiceTest {
    private final ElevenLabsTextToSpeechService textToSpeechService;

    @Test
    void testTextToSpeech() {
        final byte[] bytes = textToSpeechService.textToSpeech("Hello world!").blockFirst();

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    @Primary
    @MockBean
    public @NotNull ReactorElevenLabsHttpClient httpClient() throws IOException {
        final ReactorElevenLabsHttpClient httpClient = mock(ReactorElevenLabsHttpClient.class);

        when(httpClient.textToSpeech(notNull()))
            .thenReturn(
                Mono.just(HttpResponse.ok(
                    Files.readAllBytes(Path.of("src/test/resources/voices/Hello world!.mp3"))
                ))
            );

        return httpClient;
    }
}
