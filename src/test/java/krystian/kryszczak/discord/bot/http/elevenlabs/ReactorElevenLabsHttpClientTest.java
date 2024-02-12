package krystian.kryszczak.discord.bot.http.elevenlabs;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import krystian.kryszczak.discord.bot.model.elevenlabs.TextToSpeech;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.io.*;

import static fixture.util.WireMockUtils.wireMockEnv;
import static org.junit.jupiter.api.Assertions.*;

public final class ReactorElevenLabsHttpClientTest {
    @Test
    public void testTextToSpeech() {
        wireMockEnv(context -> {
            final ReactorElevenLabsHttpClient httpClient = context.getBean(ReactorElevenLabsHttpClient.class);
            final TextToSpeech.Factory factory = context.getBean(TextToSpeech.Factory.class);

            final Mono<HttpResponse<byte[]>> response = httpClient.textToSpeech(
                factory.createWithDefaults("Hello world!"));
            final HttpResponse<byte[]> first = response.block();
            assertNotNull(first);
            assertEquals(HttpStatus.OK, first.getStatus());
            assertTrue(first.getContentType().isPresent());
            assertEquals("audio/mpeg", first.getContentType().get().toString());

            assertDoesNotThrow(() -> {
                final File file = File.createTempFile("result", ".mpeg");
                final OutputStream outputStream = new FileOutputStream(file);

                final byte[] bytes = response.map(HttpResponse::body).block();
                assertNotNull(bytes);
                for (final byte data : bytes) {
                    outputStream.write(data);
                }
                outputStream.close();

                assertTrue(file.length() > 0);
            });
        });
    }
}
