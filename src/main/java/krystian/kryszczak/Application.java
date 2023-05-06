package krystian.kryszczak;

import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.JDA;
import picocli.CommandLine.Command;

@Command(name = "discord-bot", mixinStandardHelpOptions = true)
@NoArgsConstructor
public final class Application implements Runnable {
    @Inject
    private JDA jda;

    public static void main(String[] args) {
        PicocliRunner.run(Application.class, args);
    }

    @Override
    public void run() {}
}
