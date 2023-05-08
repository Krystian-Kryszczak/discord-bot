package krystian.kryszczak.service.conversation;

import net.dv8tion.jda.api.entities.Member;

public sealed interface ConversationService permits ChatGptConversationService {
    void replay(byte[] audioData);
}
