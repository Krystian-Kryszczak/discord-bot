package krystian.kryszczak.service.speech.text;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public final class TextToSpeechServiceTest {
    @Inject
    TextToSpeechService textToSpeechService;

    @Test
    void testTextToSpeech() {
        final File file = textToSpeechService.textToSpeech("Hello world!")
            .blockingGet();

        assertNotNull(file);
        assertTrue(file.isFile());
        assertTrue(file.length() > 0);
        assertTrue(file.getName().endsWith(".mpeg"));
    }
}
