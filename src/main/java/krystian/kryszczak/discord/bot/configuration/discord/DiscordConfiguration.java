package krystian.kryszczak.discord.bot.configuration.discord;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("discord")
public final class DiscordConfiguration {
    private String token;
    private Long audioReceiverAwaitTimeMillis;
    private Boolean autoReconnect;
}
