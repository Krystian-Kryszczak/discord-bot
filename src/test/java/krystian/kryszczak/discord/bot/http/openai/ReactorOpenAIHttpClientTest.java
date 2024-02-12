package krystian.kryszczak.discord.bot.http.openai;

import io.micronaut.http.client.multipart.MultipartBody;
import org.junit.jupiter.api.Test;

import java.io.File;

import static fixture.util.WireMockUtils.wireMockEnv;
import static org.junit.jupiter.api.Assertions.*;

public final class ReactorOpenAIHttpClientTest {
    @Test
    public void testCreateTranscription() {
        wireMockEnv(context -> {
            final ReactorOpenAIHttpClient httpClient = context.getBean(ReactorOpenAIHttpClient.class);

            final File file = new File("src/test/resources/voices/Hello world!.mp3");
            assertTrue(file.isFile());
            assertTrue(file.length() > 0);

            final String transcription = httpClient.createTranscription(
                MultipartBody.builder()
                    .addPart("file", file)
                    .addPart("model", "whisper-1")
                    .addPart("response_format", "text")
                    .build()
            ).block();

            assertNotNull(transcription);
            assertFalse(transcription.isBlank());
            assertTrue(transcription.matches("(?i).*hello world.*"));
        });
    }
}
