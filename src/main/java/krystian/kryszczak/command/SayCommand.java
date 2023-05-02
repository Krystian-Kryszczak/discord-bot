package krystian.kryszczak.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import jakarta.inject.Singleton;
import krystian.kryszczak.model.audio.TrackScheduler;
import krystian.kryszczak.service.speech.TextToSpeechService;
import lombok.SneakyThrows;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

@Singleton
public final class SayCommand extends Command {
    private final AudioPlayerManager playerManager;
    private final TrackScheduler scheduler;
    private final TextToSpeechService textToSpeechService;

    SayCommand(final AudioPlayerManager playerManager, final TrackScheduler scheduler, final TextToSpeechService textToSpeechService) {
        super("say");
        this.playerManager = playerManager;
        this.scheduler = scheduler;
        this.textToSpeechService = textToSpeechService;
    }

    @SneakyThrows
    @Override
    public Publisher<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
            .map(content -> content.split(" "))
            .filter(args -> args.length > 1)
            .map(args -> Arrays.stream(args).skip(1).collect(Collectors.joining(" "))).flux()
            .flatMap(text -> textToSpeechService.textToSpeechBufferedFile(text).toFlowable())
            .doOnNext(file -> playerManager.loadItem(file.getAbsolutePath(), scheduler))
            .then();
    }
}
