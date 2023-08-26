package krystian.kryszczak.service.conversation;

import jakarta.inject.Singleton;
import krystian.kryszczak.service.chat.ChatService;
import krystian.kryszczak.service.provider.BotAudioProviderService;
import krystian.kryszczak.service.speech.recognition.SpeechRecognitionService;
import krystian.kryszczak.service.speech.text.TextToSpeechService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Singleton
@RequiredArgsConstructor
public final class ChatGptConversationService implements ConversationService {
    private static final Logger logger = LoggerFactory.getLogger(ChatGptConversationService.class);

    private final SpeechRecognitionService speechRecognitionService;
    private final ChatService chatService;
    private final TextToSpeechService textToSpeechService;
    private final BotAudioProviderService botAudioProviderService;

    @Override
    public void replay(byte[] audioData) {
        speechRecognitionService.recognizeSpeech(audioData)
            .doOnSuccess(it -> logger.info("Recognized speech: " + it))
            .flatMapSingle(chatService::replay)
            .doAfterSuccess(it -> logger.info("Replay: " + it))
            .flatMapSingle(textToSpeechService::textToSpeech)
            .doAfterSuccess(botAudioProviderService::loadItem)
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete()
            .subscribe();
    }

    @Override
    public void replay(File wavAudioFile) {
        speechRecognitionService.recognizeSpeech(wavAudioFile)
            .doOnSuccess(it -> logger.info("Recognized speech: " + it))
            .flatMapSingle(chatService::replay)
            .doAfterSuccess(it -> logger.info("Replay: " + it))
            .flatMapSingle(textToSpeechService::textToSpeech)
            .doAfterSuccess(botAudioProviderService::loadItem)
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete()
            .subscribe();
    }
}
