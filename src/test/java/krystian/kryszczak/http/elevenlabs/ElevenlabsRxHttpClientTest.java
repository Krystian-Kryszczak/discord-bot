package krystian.kryszczak.http.elevenlabs;

import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import krystian.kryszczak.model.elevenlabs.TextToSpeech;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public final class ElevenlabsRxHttpClientTest {
    @Inject
    private ElevenLabsRxHttpClient httpClient;
    @Inject
    private TextToSpeech.Factory factory;

    @Test
    void testTextToSpeechStream() throws IOException {
        final var response = httpClient.textToSpeechStream(factory.createWithDefaults("Hello world!"));
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getContentType().isPresent());
        assertEquals("audio/mpeg", response.getContentType().get().toString());

        final File file = File.createTempFile("result", ".mpeg");
        final OutputStream outputStream = new FileOutputStream(file);

        final var responseBody = response.getBody(byte[].class);
        assertTrue(responseBody.isPresent());
        outputStream.write(responseBody.get());
        outputStream.close();

        assertTrue(file.length() > 0);
        System.out.println(file.getAbsolutePath());
    }
}
