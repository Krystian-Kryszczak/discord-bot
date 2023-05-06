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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Singleton
public final class DefaultAudioReceiver implements AudioReceiveHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAudioReceiver.class);

    private final List<byte[]> receivedBytes = new ArrayList<>();
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
        long now = System.currentTimeMillis();

        try {
            receiveAudioData(userAudio.getAudioData(1.0));

            if (latestTime > 0 && now - latestTime > 500) {
//                getWavFile(getNextFile(), decodedData);
                collectReceived();
            }
            latestTime = now;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    private void receiveAudioData(final byte @NotNull [] audio) {
        receivedBytes.add(audio);
    }

    private void collectReceived() {
        int size = 0;
        for (byte[] bs : receivedBytes) {
            size += bs.length;
        }

        final int size2 = receivedBytes.stream().mapToInt(bytes -> bytes.length).sum();

        logger.info(String.valueOf(size2 == size));

        final byte[] decodedData = new byte[size];

        int i = 0;
        for (byte[] bs : receivedBytes) {
            for (byte b : bs) {
                decodedData[i++] = b;
            }
        }
    }
}
