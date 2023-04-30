package krystian.kryszczak.configuration.openai;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("open-ai")
public class OpenAiConfiguration {
    private String token;
    private String gptModel;
}
