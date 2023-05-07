package krystian.kryszczak.model.audio.receiver;

import jakarta.inject.Singleton;
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
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

@Singleton
public final class SpeechRecognitionAudioReceiver implements AudioReceiveHandler {
    private static final Logger logger = LoggerFactory.getLogger(SpeechRecognitionAudioReceiver.class);

    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();
    private int bytesCount = 0;
    private long latestTime = 0;

    private File getNextFile() throws IOException {
        final File file = new File("./saved", "discord-" + new Random().nextInt());
        System.out.println(file.getAbsolutePath());
        return file;
    }
    private void getWavFile(File outFile, byte[] decodedData) throws IOException {
        final var audioInputStream = new AudioInputStream(new ByteArrayInputStream(decodedData), OUTPUT_FORMAT, decodedData.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outFile);
    }

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

            if (latestTime > 0 && start - latestTime > 500) {
//                getWavFile(getNextFile(), decodedData);
            }

            latestTime = start;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
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
