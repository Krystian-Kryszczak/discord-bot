package krystian.kryszczak.discord.bot.service.speech.recognition;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.jupiter.api.Assertions.*;

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

        assertNotNull(transcription);
        assertFalse(transcription.isBlank());
        assertTrue("Hello world!".length() < transcription.length());
        assertTrue("Hello world!".length() + 3 > transcription.length());
    }

    @Test
    void speechRecognitionServiceUsingBytesTest() throws IOException {
        final File file = new File("src/test/resources/voices/Hello world!.mp3");
        assertTrue(file.isFile());
        assertTrue(file.length() > 0);

        final byte[] bytes = new byte[(int) file.length()];
        try (final var raf = new RandomAccessFile(file, "r")) {
            raf.readFully(bytes);
        }

        final String transcription = speechRecognitionService
            .recognizeSpeech(bytes)
            .blockingGet();

        assertNotNull(transcription);
        assertFalse(transcription.isBlank());
        assertTrue("Hello world!".length() < transcription.length());
        assertTrue("Hello world!".length() + 3 > transcription.length());
    }
}
