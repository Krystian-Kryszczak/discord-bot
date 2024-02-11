package krystian.kryszczak.discord.bot.configuration.openai;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public final class OpenAiConfigurationTest {

    @Test
    public void tokenTest() {
        Map<String, Object> items = new HashMap<>();
        items.put("open-ai.token", "sk-test-token");
        items.put("open-ai.gpt-model", "gpt-3.5-turbo");
        items.put("open-ai.audio-model", "whisper-1");
        items.put("open-ai.language", "pl");
        items.put("open-ai.default-timeout", "60s");

        ApplicationContext ctx = ApplicationContext.run(items);
        OpenAiConfiguration openAiConfiguration = ctx.getBean(OpenAiConfiguration.class);

        String token = openAiConfiguration.getToken();
        String gptModel = openAiConfiguration.getGptModel();
        String audioModel = openAiConfiguration.getAudioModel();
        String language = openAiConfiguration.getLanguage();
        Duration defaultTimeout = openAiConfiguration.getDefaultTimeout();

        assertEquals("sk-test-token", token);
        assertEquals("gpt-3.5-turbo", gptModel);
        assertEquals("whisper-1", audioModel);
        assertEquals("pl", language);
        assertEquals(Duration.ofSeconds(60), defaultTimeout);

        ctx.close();
    }
}