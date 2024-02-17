package krystian.kryszczak.discord.bot.configuration.openai;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import java.time.Duration;

@Data
@ConfigurationProperties("open-ai")
public final class OpenAiConfiguration {
    private String token;
    private String gptModel;
    private String transcriptionModel;
    private String speechModel;
    private String speechVoice;
    private String language;
    private Duration defaultTimeout;
}
