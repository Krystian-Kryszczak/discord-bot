package krystian.kryszczak.model.audio.receiver;

import jakarta.inject.Singleton;
import krystian.kryszczak.service.conversation.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.UserAudio;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.security.SecureRandom;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Singleton
@RequiredArgsConstructor
public final class ConversationAudioReceiver implements AudioReceiveHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConversationAudioReceiver.class);
    private final ConversationService conversationService;
    private static final long AWAIT_TIME_MILLIS = 1500;

    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();
    private int bytesCount = 0;

    private final Executor executor = CompletableFuture.delayedExecutor(AWAIT_TIME_MILLIS, TimeUnit.MILLISECONDS);
    private boolean listening = true;

    @Override
    public boolean canReceiveUser() {
        return true;
    }

    @Override
    public void handleUserAudio(@NotNull UserAudio userAudio) {
        try {
            byte[] data = userAudio.getAudioData(1.0f);

            queue.add(data);
            bytesCount += data.length;

            activateListener();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    private void activateListener() {
        if (listening) return;

        CompletableFuture.runAsync(() -> {
            replay();
            listening = false;
        }, executor);
    }

    private void replay() {
        conversationService.replay(collectQueueData());
        logger.info("Replayed.");
    }

    @SneakyThrows
    private File collectQueueData() {
        final byte[] data = new byte[bytesCount];
        int i = 0;
        for (final byte[] bytes : queue) {
            for (final byte e : bytes) {
                data[i] = e;
                i++;
            }
        }
        queue.clear();
        final var audioInputStream = new AudioInputStream(new ByteArrayInputStream(data), OUTPUT_FORMAT, data.length);
        final var output = new File("storage/receiver", "discord-" + new SecureRandom().nextInt());
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, output);

        return output;
    }
}
