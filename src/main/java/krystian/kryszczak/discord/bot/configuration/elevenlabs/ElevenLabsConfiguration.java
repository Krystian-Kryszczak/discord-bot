package krystian.kryszczak.discord.bot.configuration.elevenlabs;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.Introspected;
import krystian.kryszczak.discord.bot.model.elevenlabs.VoiceSettings;
import lombok.Data;

@Data
@Introspected
@ConfigurationProperties("eleven-labs")
public final class ElevenLabsConfiguration {
    private String token;
    private String defaultVoiceId;
    private String defaultModelId;

    @ConfigurationBuilder(prefixes = "set", configurationPrefix = "default-voice-settings")
    VoiceSettings.Builder builder = VoiceSettings.builder();
}
