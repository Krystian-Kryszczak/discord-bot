package krystian.kryszczak.service.conversation;

import java.io.File;

public sealed interface ConversationService permits ChatGptConversationService {
    void replay(byte[] audioData);
    void replay(File audioFile);
}
