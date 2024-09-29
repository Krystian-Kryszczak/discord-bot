package krystian.kryszczak.discord.bot.service.chat;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@RequiredArgsConstructor
public final class Gpt2PyTorchChatBotServiceTest {
    private final @Named("Gpt2PyTorch") ChatBotService chatBotService;

    @Test
    public void chatServiceTest() {
        final String replay = chatBotService.createChatCompletion("DeepMind Company is")
            .blockLast();

        assertNotNull(replay);
        assertFalse(replay.isBlank());
        assertTrue(replay.split(" ").length > 5);
    }
}
