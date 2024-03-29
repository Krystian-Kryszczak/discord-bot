package krystian.kryszczak.discord.bot.command;

import jakarta.inject.Singleton;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class PingCommand extends Command {
    PingCommand() {
        super("ping", "Calculate ping of the bot");
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true)
            .flatMap(v ->
                event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)
            ).queue();
    }
}
