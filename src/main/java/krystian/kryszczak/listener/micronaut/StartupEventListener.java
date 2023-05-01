package krystian.kryszczak.listener.micronaut;

import discord4j.core.GatewayDiscordClient;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import krystian.kryszczak.command.Command;
import krystian.kryszczak.listener.discord.DiscordEventListener;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@AllArgsConstructor
public final class StartupEventListener {
    private static final Logger logger = LoggerFactory.getLogger(StartupEventListener.class);

    private final ApplicationContext applicationContext;
    private final GatewayDiscordClient gatewayDiscordClient;

    @EventListener
    public void onStartupEvent(StartupEvent event) {
        loadDiscordListeners();
        loadDiscordCommands();
    }

    private void loadDiscordListeners() {
        applicationContext.getBeanDefinitions(DiscordEventListener.class)
            .forEach(bean -> {
                final String fullName = bean.getName();
                final String[] splitFullName = fullName.split("\\.");
                final String name = splitFullName[splitFullName.length-1];

                applicationContext.getBean(bean.getBeanType());

                logger.info("Discord Listener \"" + name + "\" has been loaded!");
            });
    }

    private void loadDiscordCommands() {
        applicationContext.getBeanDefinitions(Command.class)
            .forEach(bean -> {
                final String fullName = bean.getName();
                final String[] splitFullName = fullName.split("\\.");
                final String className = splitFullName[splitFullName.length-1];

                final var command = applicationContext.getBean(bean.getBeanType());
                final String commandName = command.getName();
                Command.commands.put(commandName, command);

                logger.info("Discord Command \"" + className + "\" (" + commandName + ") has been loaded!");
            });
    }
}
