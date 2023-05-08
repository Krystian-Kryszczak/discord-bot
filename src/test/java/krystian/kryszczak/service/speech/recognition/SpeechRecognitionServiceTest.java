package krystian.kryszczak.service.speech.recognition;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public final class SpeechRecognitionServiceTest {
    @Inject
    private SpeechRecognitionService speechRecognitionService;

    @Test
    void speechRecognitionServiceTest() {
        final File file = new File("src/test/resources/voices/Hello world!.mp3");
        assertTrue(file.isFile());
        assertTrue(file.length() > 0);

        final String transcription = speechRecognitionService
            .recognizeSpeech(file)
            .blockingGet();

        assertFalse(transcription.isBlank());
        assertTrue("Hello world!".length() < transcription.length());
        assertTrue("Hello world!".length() + 3 > transcription.length());
    }
}
