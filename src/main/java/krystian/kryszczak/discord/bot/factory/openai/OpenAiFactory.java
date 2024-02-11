package krystian.kryszczak.discord.bot.factory.openai;

import com.theokanning.openai.service.OpenAiService;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.configuration.openai.OpenAiConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Factory
public final class OpenAiFactory {
    @Contract("_ -> new")
    @Singleton
    public @NotNull OpenAiService openAiService(final @NotNull OpenAiConfiguration openAiConfiguration) {
        return new OpenAiService(openAiConfiguration.getToken(), openAiConfiguration.getDefaultTimeout());
    }
}
