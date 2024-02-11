package krystian.kryszczak.discord.bot.audio.scheduler;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor
public final class TrackScheduler implements AudioLoadResultHandler {
    private final AudioPlayer player;

    @Override
    public void trackLoaded(final AudioTrack track) {
        player.playTrack(track);
    }

    @Override
    public void playlistLoaded(final @NotNull AudioPlaylist playlist) {
        player.playTrack(playlist.getSelectedTrack());
    }

    @Override
    public void noMatches() {}

    @Override
    public void loadFailed(final FriendlyException exception) {}
}
