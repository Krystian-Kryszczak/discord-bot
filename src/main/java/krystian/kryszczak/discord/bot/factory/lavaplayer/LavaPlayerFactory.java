package krystian.kryszczak.discord.bot.factory.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public final class LavaPlayerFactory {

    @Singleton
    public AudioPlayerManager audioPlayerManager() {
        final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

        playerManager.getConfiguration()
            .setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);

        playerManager.registerSourceManager(new LocalAudioSourceManager());

        AudioSourceManagers.registerRemoteSources(playerManager);

        return playerManager;
    }

    @Singleton
    public AudioPlayer audioPlayer(final AudioPlayerManager playerManager) {
        return playerManager.createPlayer();
    }
}
