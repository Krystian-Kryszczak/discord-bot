package krystian.kryszczak.discord.bot.service.conversation;

import jakarta.inject.Singleton;
import krystian.kryszczak.discord.bot.service.chat.ChatBotService;
import krystian.kryszczak.discord.bot.service.provider.BotAudioProviderService;
import krystian.kryszczak.discord.bot.service.transcription.TranscriptionService;
import krystian.kryszczak.discord.bot.service.speech.text.TextToSpeechService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void replay(byte[] audioData) {
        transcriptionService.createTranscription(audioData)
            .doOnSuccess(this::recognizedSpeechLog)
            .flatMapMany(chatBotService::createChatCompletion)
            .doOnNext(this::replyLog)
            .flatMap(textToSpeechService::textToSpeech)
            .doOnNext(botAudioProviderService::loadItem)
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete()
            .subscribe();
    }

    @Override
    public void replay(File wavAudioFile) {
        transcriptionService.createTranscription(wavAudioFile)
            .doOnSuccess(this::recognizedSpeechLog)
            .flatMapMany(chatBotService::createChatCompletion)
            .doOnNext(this::replyLog)
            .flatMap(textToSpeechService::textToSpeech)
            .doOnNext(botAudioProviderService::loadItem)
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete()
            .subscribe();
    }

    private void recognizedSpeechLog(String data) {
        logger.info("Recognized speech: {}", data);
    }

    private void replyLog(String data) {
        logger.info("Replay: {}", data);
    }
}
