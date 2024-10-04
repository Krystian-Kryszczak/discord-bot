package krystian.kryszczak.discord.bot.service.conversation;

import net.dv8tion.jda.api.audio.UserAudio;

import java.io.File;

public sealed interface ConversationService permits ChatGptConversationService {
    void replay(byte[] wavAudioData, UserAudio userAudio);
    void replay(File wavAudioFile, UserAudio userAudio);
}
