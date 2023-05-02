package krystian.kryszczak.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import jakarta.inject.Singleton;
import krystian.kryszczak.model.audio.scheduler.TrackScheduler;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public final class PlayCommand extends Command {
    private final AudioPlayerManager playerManager;
    private final TrackScheduler scheduler;

    PlayCommand(final AudioPlayerManager playerManager, final TrackScheduler scheduler) {
        super("play");
        this.playerManager = playerManager;
        this.scheduler = scheduler;
    }

    @Override
    public Publisher<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
            .map(content -> content.split(" "))
            .filter(args -> args.length > 1)
            .doOnNext(args -> playerManager.loadItem(args[1], scheduler))
            .then();
    }
}
