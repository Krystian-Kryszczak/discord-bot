package krystian.kryszczak.listener.discord.command;

import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import krystian.kryszczak.command.Command;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor
public final class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(final @NotNull SlashCommandInteractionEvent event) {
        Maybe.just(event.getName())
            .map(Command.commands::get)
            .doAfterSuccess(it -> it.execute(event))
            .switchIfEmpty(
                Maybe.create(sink -> {
                    event.reply("Error: Command not found!").queue();
                    sink.onComplete();
                }))
            .subscribe();
    }
}
