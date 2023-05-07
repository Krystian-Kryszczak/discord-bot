package krystian.kryszczak.http.openai;

import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import krystian.kryszczak.configuration.openai.OpenAiConfiguration;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public final class OpenAiRxHttpClientTest {
    @Inject
    private OpenAiRxHttpClient httpClient;
    @Inject
    private OpenAiConfiguration configuration;

    @Test
    void testCreateTranscription() {
        final File file = new File("src/test/resources/voices/Hello world!.mp3");
        assertTrue(file.isFile());
        assertTrue(file.length() > 0);

        final var transcription = httpClient.createTranscription(
                MultipartBody.builder()
                    .addPart("model", configuration.getAudioModel())
                    .addPart("file", file)
                    .build()
                ).blockingGet();

        assertEquals("Hello world!", transcription);
    }
}
