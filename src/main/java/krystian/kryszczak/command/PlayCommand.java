package krystian.kryszczak.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import jakarta.inject.Singleton;
import krystian.kryszczak.model.lavaplayer.TrackScheduler;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Singleton
public final class PlayCommand extends Command {
    private final AudioPlayerManager playerManager;
    private final TrackScheduler scheduler;

    public PlayCommand(final AudioPlayerManager playerManager, final TrackScheduler scheduler) {
        super("play");
        this.playerManager = playerManager;
        this.scheduler = scheduler;
    }

    @Override
    public Publisher<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
            .map(content -> Arrays.asList(content.split(" ")))
            .filter(args -> args.size() > 1)
            .doOnNext(args -> playerManager.loadItem(args.get(1), scheduler))
            .then();
    }
}
