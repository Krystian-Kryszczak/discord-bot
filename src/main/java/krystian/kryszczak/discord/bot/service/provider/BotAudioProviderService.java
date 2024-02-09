package krystian.kryszczak.discord.bot.service.provider;

public sealed interface BotAudioProviderService permits BotAudioProviderServiceImpl {
    void loadItem(final String identifier);
    void loadItem(final byte[] audioData);
}
