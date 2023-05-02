package krystian.kryszczak.service.speech;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public final class TextToSpeechServiceTest {
    @Inject
    TextToSpeechService textToSpeechService;

    @Test
    void testTextToSpeech() { // TODO
        textToSpeechService.textToSpeechBufferedFile("Hello world!");
    }
}
