package krystian.kryszczak.service.chat;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.openai.OpenAiConfiguration;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public final class ChatGptService implements ChatService {
    private final OpenAiService openAiService;
    private final OpenAiConfiguration configuration;

    @Override
    public Single<String> replay(@NotNull String message) {
        return Flowable.fromIterable(
            openAiService.createChatCompletion(
                ChatCompletionRequest.builder()
                    .model(configuration.getGptModel())
                    .messages(List.of(new ChatMessage(ChatMessageRole.USER.value(), message)))
                    .build()
            ).getChoices())
        .map(ChatCompletionChoice::getMessage)
        .map(ChatMessage::getContent)
        .collect(Collectors.joining());
    }
}
