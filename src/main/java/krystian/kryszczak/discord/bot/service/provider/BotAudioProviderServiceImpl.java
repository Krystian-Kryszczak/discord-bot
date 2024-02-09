package krystian.kryszczak.discord.bot.service.provider;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.audio.scheduler.TrackScheduler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;

@Singleton
@RequiredArgsConstructor
public final class BotAudioProviderServiceImpl implements BotAudioProviderService {
    private final AudioPlayerManager playerManager;
    private final TrackScheduler scheduler;

    @Override
    public void loadItem(String identifier) {
        playerManager.loadItem(identifier, scheduler);
    }

    @SneakyThrows
    @Override
    public void loadItem(byte[] audioData) {
        final File file = File.createTempFile("discord", ".mpeg");
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(audioData);
        }
        playerManager.loadItem(file.getAbsolutePath(), scheduler);
        file.deleteOnExit();
    }
}
