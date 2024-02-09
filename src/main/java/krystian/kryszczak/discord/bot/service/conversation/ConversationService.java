package krystian.kryszczak.discord.bot.service.conversation;

import java.io.File;

public sealed interface ConversationService permits ChatGptConversationService {
    void replay(byte[] wavAudioData);
    void replay(File wavAudioFile);
}
