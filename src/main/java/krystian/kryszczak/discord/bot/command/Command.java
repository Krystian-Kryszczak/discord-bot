package krystian.kryszczak.discord.bot.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class Command {
    private final String name;
    private final String description;
    private final OptionData[] options;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.options = new OptionData[0];
    }

    public static final Map<String, Command> commands = new HashMap<>();

    public abstract void execute(final @NotNull SlashCommandInteractionEvent event);
}
