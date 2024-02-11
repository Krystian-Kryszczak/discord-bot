package krystian.kryszczak.discord.bot.listener.discord.command;

import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.command.Command;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

@Singleton
@RequiredArgsConstructor
public final class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(final @NotNull SlashCommandInteractionEvent event) {
        Mono.just(event.getName())
            .map(Command.commands::get)
            .doOnSuccess(it -> it.execute(event))
            .switchIfEmpty(
                Mono.create(sink -> {
                    event.reply("Error: Command not found!").queue();
                    sink.success();
                }))
            .subscribe();
    }
}
