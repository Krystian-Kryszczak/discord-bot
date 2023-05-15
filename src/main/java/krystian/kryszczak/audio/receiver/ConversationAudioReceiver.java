package krystian.kryszczak.audio.receiver;

import jakarta.inject.Singleton;
import krystian.kryszczak.configuration.discord.DiscordConfiguration;
import krystian.kryszczak.service.conversation.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.UserAudio;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Singleton
public final class ConversationAudioReceiver implements AudioReceiveHandler {
    private final ConversationService conversationService;

    public ConversationAudioReceiver(ConversationService conversationService, DiscordConfiguration configuration) {
        this.conversationService = conversationService;
        this.executor = CompletableFuture.delayedExecutor(configuration.getAudioReceiverAwaitTimeMillis(), TimeUnit.MILLISECONDS);
    }

    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();
    private int bytesCount = 0;

    private final Executor executor;
    private boolean listening = false;

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
        listening = true;

        CompletableFuture.runAsync(() -> {
            conversationService.replay(collectQueueData());
            CompletableFuture
                .runAsync(() -> listening = false);
        }, executor);
    }

    @SneakyThrows
    private byte[] collectQueueData() {
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
        final var output = new ByteArrayOutputStream();
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, output);

        return output.toByteArray();
    }
}
