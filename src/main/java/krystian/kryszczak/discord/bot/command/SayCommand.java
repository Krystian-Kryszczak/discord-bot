package krystian.kryszczak.discord.bot.command;

import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.service.provider.BotAudioProviderService;
import krystian.kryszczak.discord.bot.service.speech.text.TextToSpeechService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Singleton
public final class SayCommand extends Command {
    private static final Logger logger = LoggerFactory.getLogger(SayCommand.class);
    private static final String ARG_NAME = "phrase";

    private final BotAudioProviderService botAudioProviderService;
    private final TextToSpeechService textToSpeechService;

    SayCommand(final BotAudioProviderService botAudioProviderService, final TextToSpeechService textToSpeechService) {
        super("say", "The bot will speak your command.", new OptionData[] {
            new OptionData(OptionType.STRING, ARG_NAME, "Phrase to say by Bot.").setRequired(true)
        });
        this.botAudioProviderService = botAudioProviderService;
        this.textToSpeechService = textToSpeechService;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Maybe.just(Objects.requireNonNull(event.getOption(ARG_NAME)))
            .onErrorComplete()
            .map(OptionMapping::getAsString)
            .filter(phrase -> !phrase.isBlank())
            .doOnSuccess(
                phrase -> textToSpeechService.textToSpeech(phrase)
                .doAfterSuccess(botAudioProviderService::loadItem)
                .subscribe()
            )
            .map(phrase -> "I saying: \"" + phrase + "\"")
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorReturnItem("Error")
            .defaultIfEmpty("You must define valid phrase to say for me!")
            .doAfterSuccess(it -> event.reply(it).setEphemeral(true).queue())
            .subscribe();
    }
}
