package krystian.kryszczak.discord.bot.service.chat;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

public sealed interface ChatService permits ChatGptService {
    /**
     * Creates a model response for the given chat conversation.
     * @param message input user message
     * @return streaming response of chat completion
     */
    Flux<String> createChatCompletion(@NotNull String message);
}
