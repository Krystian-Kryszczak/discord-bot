package krystian.kryszczak.discord.bot.configuration.openai;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import java.time.Duration;

@Data
@ConfigurationProperties("open-ai")
public class OpenAiConfiguration {
    private String token;
    private String gptModel;
    private String audioModel;
    private String language;
    private Duration defaultTimeout;
}
