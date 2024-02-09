package krystian.kryszczak.discord.bot.service.speech.text;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public final class TextToSpeechServiceTest {
    @Inject
    TextToSpeechService textToSpeechService;

    @Test
    void testTextToSpeech() {
        final byte[] bytes = textToSpeechService.textToSpeech("Hello world!")
            .blockingGet();

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }
}
