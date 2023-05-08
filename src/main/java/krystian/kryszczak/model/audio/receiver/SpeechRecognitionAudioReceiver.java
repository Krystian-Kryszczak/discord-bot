package krystian.kryszczak.model.audio.receiver;

import jakarta.inject.Singleton;
import krystian.kryszczak.service.conversation.ConversationService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.UserAudio;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Singleton
@RequiredArgsConstructor
public final class SpeechRecognitionAudioReceiver implements AudioReceiveHandler {
    private static final Logger logger = LoggerFactory.getLogger(SpeechRecognitionAudioReceiver.class);
    private final ConversationService conversationService;

    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();
    private int bytesCount = 0;
    private long latestTime = 0;

    private static final long AWAIT_TIME_MILLIS = 500;
    private static final Executor executor = CompletableFuture.delayedExecutor((int) (AWAIT_TIME_MILLIS * 1.1), TimeUnit.MILLISECONDS);
    private static boolean watcherActive = false;

    @Override
    public boolean canReceiveUser() {
        return true;
    }

    @Override
    public void handleUserAudio(@NotNull UserAudio userAudio) {
        long start = System.currentTimeMillis();

        try {
            byte[] data = userAudio.getAudioData(1.0f);

            queue.add(data);
            bytesCount += data.length;

            activateWatcher(start);

            latestTime = start;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    private void activateWatcher(long startTime) {
        if (watcherActive) return;
        watcherActive = true;
        logger.info("Activated Watcher.");
        CompletableFuture.runAsync(() -> {
            logger.info("Watcher Call");

            logger.info("");
            logger.info("latestTime: " + latestTime);
            logger.info("startTime: " + startTime);
            logger.info("startTime - latestTime: " + (latestTime - startTime));
            logger.info("AWAIT_TIME_MILLIS: " + AWAIT_TIME_MILLIS);
            logger.info("latestTime > 0: " + (latestTime > 0 ));
            logger.info("startTime - latestTime > AWAIT_TIME_MILLI: " + (latestTime - startTime > AWAIT_TIME_MILLIS));
            logger.info("");

            if (latestTime > 0 && latestTime - startTime > AWAIT_TIME_MILLIS) {
                logger.info("Watcher before replay.");
                conversationService.replay(collectQueueData());
                logger.info("Watcher after replay.");
                watcherActive = false;
                logger.info("Watcher reset.");
            }
        }, executor);
    }

    private byte[] collectQueueData() {
        final byte[] result = new byte[bytesCount];
        int i = 0;
        for (final byte[] bytes : queue) {
            for (final byte e : bytes) {
                result[i] = e;
                i++;
            }
        }
        queue.clear();
        return result;
    }
}
