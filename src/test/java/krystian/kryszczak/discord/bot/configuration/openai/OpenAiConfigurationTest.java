package krystian.kryszczak.discord.bot.configuration.openai;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class OpenAiConfigurationTest {
    @Test
    public void openAiConfiguration() {
        Map<String, Object> items = new HashMap<>();
        items.put("open-ai.token", "sk-test-token");
        items.put("open-ai.gpt-model", "gpt-3.5-turbo");
        items.put("open-ai.transcription-model", "whisper-1");
        items.put("open-ai.speech-model", "tts-1");
        items.put("open-ai.speech-voice", "alloy");
        items.put("open-ai.language", "pl");
        items.put("open-ai.default-timeout", "60s");

        final ApplicationContext ctx = ApplicationContext.run(items);
        final OpenAiConfiguration openAiConfiguration = ctx.getBean(OpenAiConfiguration.class);

        final String token = openAiConfiguration.getToken();
        final String gptModel = openAiConfiguration.getGptModel();
        final String transcriptionModel = openAiConfiguration.getTranscriptionModel();
        final String language = openAiConfiguration.getLanguage();
        final Duration defaultTimeout = openAiConfiguration.getDefaultTimeout();

        assertEquals("sk-test-token", token);
        assertEquals("gpt-3.5-turbo", gptModel);
        assertEquals("whisper-1", transcriptionModel);
        assertEquals("pl", language);
        assertEquals(Duration.ofSeconds(60), defaultTimeout);

        ctx.close();
    }
}
