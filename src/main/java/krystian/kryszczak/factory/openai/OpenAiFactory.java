package krystian.kryszczak.factory.openai;

import com.theokanning.openai.service.OpenAiService;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.openai.OpenAiConfiguration;

@Factory
public final class OpenAiFactory {

    @Singleton
    public OpenAiService openAiService(OpenAiConfiguration openAiConfiguration) {
        return new OpenAiService(openAiConfiguration.getToken());
    }
}
