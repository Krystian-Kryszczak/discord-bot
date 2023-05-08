package krystian.kryszczak.service.chat;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public final class ChatServiceTest {
    @Inject
    private ChatService chatService;

    @Test
    void chatServiceTest() {
        final String replay = chatService.replay("Hi!")
                .blockingGet();

        System.out.println(replay);

        assertNotNull(replay);
        assertFalse(replay.isBlank());
        assertTrue(replay.split(" ").length > 5);
    }
}
