package krystian.kryszczak.http.elevenlabs;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import krystian.kryszczak.model.elevenlabs.TextToSpeech;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public final class ElevenLabsRxHttpClientTest {
    @Inject
    private ElevenLabsRxHttpClient httpClient;
    @Inject
    private TextToSpeech.Factory factory;

    @Test
    void testTextToSpeech() throws IOException {
        final var response = httpClient.textToSpeechStream(factory.createWithDefaults("Hello world!"));
        final var first = response.blockingFirst();
        assertEquals(HttpStatus.OK, first.getStatus());
        assertTrue(first.getContentType().isPresent());
        assertEquals("audio/mpeg", first.getContentType().get().toString());

        final File file = File.createTempFile("result", ".mpeg");
        final OutputStream outputStream = new FileOutputStream(file);

        for (final var bytes : response.map(HttpResponse::body).collect(Collectors.toList()).blockingGet()) {
            outputStream.write(bytes);
        }
        outputStream.close();

        assertTrue(file.length() > 0);
        System.out.println(file.getAbsolutePath());
    }
}
