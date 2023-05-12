package krystian.kryszczak.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import krystian.kryszczak.audio.scheduler.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Optional;

@Singleton
public final class PlayCommand extends Command {
    private static final String URL = "url";

    private final AudioPlayerManager playerManager;
    private final TrackScheduler scheduler;

    PlayCommand(final AudioPlayerManager playerManager, final TrackScheduler scheduler) {
        super("play", "Play music from YouTube.", new OptionData[] {
            new OptionData(OptionType.STRING, URL, "Url to YouTube video.").setRequired(true)
        });
        this.playerManager = playerManager;
        this.scheduler = scheduler;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Maybe.fromOptional(Optional.ofNullable(event.getOption(URL)))
            .map(OptionMapping::getAsString)
            .doAfterSuccess(url -> playerManager.loadItem(url, scheduler))
            .map(url -> "I'm playing: " + url)
            .onErrorReturn(throwable -> "Error: " + throwable.getMessage())
            .defaultIfEmpty("You must define valid youtube video url!")
            .doAfterSuccess(it -> event.reply(it).setEphemeral(true).queue())
            .subscribe();
    }
}
