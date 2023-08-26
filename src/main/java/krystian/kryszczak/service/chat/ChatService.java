package krystian.kryszczak.service.chat;

import io.reactivex.rxjava3.core.Single;
import org.jetbrains.annotations.NotNull;

public sealed interface ChatService permits ChatGptService {
    Single<String> replay(@NotNull String message);
}
