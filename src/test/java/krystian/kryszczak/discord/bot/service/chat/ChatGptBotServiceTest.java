package krystian.kryszczak.discord.bot.service.chat;

import io.micronaut.context.annotation.Primary;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Named;
import krystian.kryszczak.discord.bot.http.openai.ReactorOpenAIHttpClient;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@RequiredArgsConstructor
public final class ChatGptBotServiceTest {
    private final @Named("ChatGpt") ChatBotService chatBotService;

    @Test
    public void chatServiceTest() {
        final String replay = chatBotService.createChatCompletion("Hello!").blockLast();

        assertNotNull(replay);
        assertFalse(replay.isBlank());
        assertTrue(replay.split(" ").length > 5);
    }

    @Primary
    @MockBean
    public @NotNull ReactorOpenAIHttpClient httpClient() {
        final ReactorOpenAIHttpClient httpClient = mock(ReactorOpenAIHttpClient.class);

        final String responseMock = """
        {
          "id": "chatcmpl-123",
          "object": "chat.completion",
          "created": 1677652288,
          "model": "gpt-3.5-turbo-0613",
          "system_fingerprint": "fp_44709d6fcb",
          "choices": [{
            "index": 0,
            "message": {
              "role": "assistant",
              "content": "Hello there, how may I assist you today?"
            },
            "logprobs": null,
            "finish_reason": "stop"
          }],
          "usage": {
            "prompt_tokens": 9,
            "completion_tokens": 12,
            "total_tokens": 21
          }
        }
        """;

        when(httpClient.createChatCompletion(notNull()))
            .thenReturn(Flux.just(responseMock));

        return httpClient;
    }
}
