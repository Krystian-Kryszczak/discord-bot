package krystian.kryszczak.discord.bot.listener.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.command.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@AllArgsConstructor
public final class StartupEventListener {
    private static final Logger logger = LoggerFactory.getLogger(StartupEventListener.class);

    private final ApplicationContext applicationContext;
    private final JDA jda;

    @EventListener
    public void onStartupEvent(StartupEvent event) {
        loadDiscordListeners();
        loadDiscordCommands();
    }

    private void loadDiscordListeners() {
        applicationContext.getBeanDefinitions(ListenerAdapter.class)
            .forEach(beanDefinition -> {
                final String fullName = beanDefinition.getName();
                final String[] splitFullName = fullName.split("\\.");
                final String name = splitFullName[splitFullName.length-1];

                final var bean = applicationContext.getBean(beanDefinition.getBeanType());
                jda.addEventListener(bean);

                logger.info("Discord Listener \"" + name + "\" has been loaded!");
            });
    }

    private void loadDiscordCommands() {
        final var definitions = applicationContext.getBeanDefinitions(Command.class);
        final var iterator = definitions.iterator();

        SlashCommandData[] commandsData = new SlashCommandData[definitions.size()];

        int i = 0;
        while (iterator.hasNext()) {
            final BeanDefinition<Command> beanDefinition = iterator.next();

            final String fullName = beanDefinition.getName();
            final String[] splitFullName = fullName.split("\\.");
            final String className = splitFullName[splitFullName.length-1];

            final var command = applicationContext.getBean(beanDefinition.getBeanType());
            final String commandName = command.getName();

            commandsData[i] = Commands.slash(command.getName(), command.getDescription())
                .addOptions(command.getOptions());

            Command.commands.put(commandName, command);

            logger.info("Discord Command \"" + className + "\" (" + commandName + ") has been loaded!");

            i++;
        }

        jda.updateCommands()
            .addCommands(commandsData)
            .queue();
    }
}
