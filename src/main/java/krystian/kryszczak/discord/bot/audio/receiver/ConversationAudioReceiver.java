package krystian.kryszczak.discord.bot.audio.receiver;

import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.configuration.discord.DiscordConfiguration;
import krystian.kryszczak.discord.bot.service.conversation.ConversationService;
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
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Singleton
public final class ConversationAudioReceiver implements AudioReceiveHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConversationAudioReceiver.class);
    private final ConversationService conversationService;

    public ConversationAudioReceiver(ConversationService conversationService, @NotNull DiscordConfiguration configuration) {
        this.conversationService = conversationService;

        final var awaitTimeMillis = configuration.getAudioReceiverAwaitTimeMillis();
        final TimeUnit unit = TimeUnit.MILLISECONDS;

        this.executor = CompletableFuture.delayedExecutor(awaitTimeMillis, unit);
        logger.info("Created a delayed executor with a delay of {} {}.", awaitTimeMillis, TimeUnit.MILLISECONDS.name().toLowerCase());
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

            activateListener(userAudio);
        } catch (OutOfMemoryError e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void activateListener(UserAudio userAudio) {
        if (listening) return;
        listening = true;

        CompletableFuture.runAsync(() -> {
            conversationService.replay(collectQueueData(), userAudio);
            CompletableFuture
                .runAsync(() -> listening = false);
        }, executor);
    }

    @SneakyThrows
    private byte @NotNull [] collectQueueData() {
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
