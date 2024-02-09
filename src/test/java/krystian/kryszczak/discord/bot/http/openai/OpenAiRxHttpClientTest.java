package krystian.kryszczak.discord.bot.http.openai;

import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import krystian.kryszczak.discord.bot.configuration.openai.OpenAiConfiguration;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

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
                .addPart("file", file)
                .addPart("model", configuration.getAudioModel())
                .addPart("response_format", "text")
                .build()
            ).blockingGet();

        assertFalse(transcription.isBlank());
        assertTrue("Hello world!".length() < transcription.length());
        assertTrue("Hello world!".length() + 3 > transcription.length());
    }
}
