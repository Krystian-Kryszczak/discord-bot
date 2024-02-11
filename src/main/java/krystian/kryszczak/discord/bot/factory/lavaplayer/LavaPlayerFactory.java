package krystian.kryszczak.discord.bot.factory.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Factory
public final class LavaPlayerFactory {
    @Singleton
    public @NotNull AudioPlayerManager audioPlayerManager() {
        final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

        playerManager.getConfiguration()
            .setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);

        playerManager.registerSourceManager(new LocalAudioSourceManager());

        AudioSourceManagers.registerRemoteSources(playerManager);

        return playerManager;
    }

    @Singleton
    public AudioPlayer audioPlayer(final @NotNull AudioPlayerManager playerManager) {
        return playerManager.createPlayer();
    }
}
