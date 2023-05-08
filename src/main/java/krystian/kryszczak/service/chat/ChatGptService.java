package krystian.kryszczak.service.chat;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.openai.OpenAiConfiguration;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public final class ChatGptService implements ChatService {
    private final OpenAiService openAiService;
    private final OpenAiConfiguration configuration;

    @Override
    public Single<String> replay(@NotNull String message) {
        return Flowable.fromIterable(
            openAiService.createCompletion(
                CompletionRequest.builder()
                    .prompt(message)
                    .model(configuration.getGptModel())
                    .echo(true)
                    .build()
            ).getChoices())
        .map(Object::toString)
        .collect(Collectors.joining());
    }
}
