package krystian.kryszczak.discord.bot.service.chat;

import io.micronaut.context.annotation.Secondary;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.configuration.openai.OpenAiConfiguration;
import krystian.kryszczak.discord.bot.http.openai.ReactorOpenAIHttpClient;
import krystian.kryszczak.discord.bot.model.openai.completion.ChatCompletion;
import krystian.kryszczak.discord.bot.model.openai.completion.Choice;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;
import krystian.kryszczak.discord.bot.model.openai.completion.request.ChatCompletionRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Secondary
@Singleton
@RequiredArgsConstructor
public final class ChatGptBotService implements ChatBotService {
    private final ReactorOpenAIHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final OpenAiConfiguration configuration;

    @Override
    public @NotNull Flux<String> createChatCompletion(final @NotNull String message) {
        return httpClient.createChatCompletion(
            ChatCompletionRequestBody.builder()
                .model(configuration.getGptModel())
                .messages(
                    new Message[] {
                        Message.builder().role("system").content("You are a helpful Discord server assistant.").build(),
                        Message.builder().role("user").content(message).build()
                    }
                ).build()
        ).mapNotNull(this::readValue)
        .mapNotNull(ChatCompletion::choices)
        .flatMapIterable(Arrays::asList)
        .mapNotNull(Choice::message)
        .mapNotNull(Message::content);
    }

    @SneakyThrows
    private ChatCompletion readValue(String data) {
        return objectMapper.readValue(data, ChatCompletion.class);
    }
}
