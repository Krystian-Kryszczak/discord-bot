package krystian.kryszczak.model.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@AllArgsConstructor
public final class TrackScheduler implements AudioLoadResultHandler {
    private final static Logger logger = LoggerFactory.getLogger(TrackScheduler.class);
    private final AudioPlayer player;

    @Override
    public void trackLoaded(final AudioTrack track) {
        logger.info("trackLoaded");
        player.playTrack(track);
    }

    @Override
    public void playlistLoaded(final AudioPlaylist playlist) {
        logger.info("playlistLoaded");
    }

    @Override
    public void noMatches() {
        logger.info("noMatches");
    }

    @Override
    public void loadFailed(final FriendlyException exception) {
        logger.info("loadFailed");
    }
}
