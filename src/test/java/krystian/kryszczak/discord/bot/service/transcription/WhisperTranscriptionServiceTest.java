package krystian.kryszczak.discord.bot.service.transcription;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@RequiredArgsConstructor
public final class WhisperTranscriptionServiceTest {
    private final @Named("Whisper") TranscriptionService transcriptionService;

    @ParameterizedTest
    @MethodSource("pathnameProvider")
    public void createTranscriptionReturnsExceptedValue(String pathname, String excepted) {
        // given
        final File file = new File(pathname);
        assertTrue(file.isFile());
        assertTrue(file.length() > 0);

        // when
        final String transcription = transcriptionService
            .createTranscription(file)
            .block();

        // then
        assertNotNull(transcription);
        assertFalse(transcription.isBlank());
        assertEquals(excepted, transcription);
    }

    @Contract(" -> new")
    public static @Unmodifiable List<Arguments> pathnameProvider() {
        return List.of(
            Arguments.of("src/test/resources/voices/hello_world.mp3", "Hello world."),
            Arguments.of("src/test/resources/voices/it's_our_time.wav", "It's our time to make a move. " +
                "It's our time to make amends. " +
                "It's our time to break the rules. " +
                "Let's begin.")
        );
    }
}
