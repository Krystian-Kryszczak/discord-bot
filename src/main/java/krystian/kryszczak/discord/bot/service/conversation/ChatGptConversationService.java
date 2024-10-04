package krystian.kryszczak.discord.bot.service.conversation;

import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.service.chat.ChatBotService;
import krystian.kryszczak.discord.bot.service.provider.BotAudioProviderService;
import krystian.kryszczak.discord.bot.service.transcription.TranscriptionService;
import krystian.kryszczak.discord.bot.service.speech.text.TextToSpeechService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.audio.UserAudio;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.File;

@Singleton
@RequiredArgsConstructor
public final class ChatGptConversationService implements ConversationService {
    private static final Logger logger = LoggerFactory.getLogger(ChatGptConversationService.class);

    private final TranscriptionService transcriptionService;
    private final ChatBotService chatBotService;
    private final TextToSpeechService textToSpeechService;
    private final BotAudioProviderService botAudioProviderService;

    @Override
    public void replay(byte[] audioData, UserAudio userAudio) {
        reply(transcriptionService.createTranscription(audioData), userAudio);
    }

    @Override
    public void replay(File wavAudioFile, UserAudio userAudio) {
        reply(transcriptionService.createTranscription(wavAudioFile), userAudio);
    }

    private void reply(@NotNull Mono<String> transcription, UserAudio userAudio) {
        transcription
            .doOnSuccess(data -> logger.info("Recognized speech: {}", data))
            .flatMapMany(chatBotService::createChatCompletion)
            .doOnNext(data -> logger.info("Replay: {}", data))
            .flatMap(textToSpeechService::textToSpeech)
            .doOnNext(botAudioProviderService::loadItem)
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete()
            .subscribe();
    }
}
