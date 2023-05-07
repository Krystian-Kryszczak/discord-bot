package krystian.kryszczak.configuration.discord;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("discord")
public class DiscordConfiguration {
    private String token;
}
