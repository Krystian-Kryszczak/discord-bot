package krystian.kryszczak.service.conversation;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import jakarta.inject.Singleton;
import krystian.kryszczak.model.audio.scheduler.TrackScheduler;
import krystian.kryszczak.service.chat.ChatService;
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
    private final AudioPlayerManager audioPlayerManager;
    private final TrackScheduler scheduler;

    private boolean active = false;

    @Override
    public void replay(byte[] audioData) {
        if (active) return;
        speechRecognitionService.recognizeSpeech(audioData)
            .doAfterSuccess(it -> logger.info("Recognized speech: " + it))
            .flatMapSingle(chatService::replay)
            .doAfterSuccess(it -> logger.info("Replay: " + it))
            .flatMapSingle(textToSpeechService::textToSpeechBufferedFile)
            .doAfterSuccess(it -> logger.info("File with voice response: " + it.getAbsolutePath()))
            .map(File::getAbsolutePath)
            .doAfterSuccess(it -> audioPlayerManager.loadItem(it, scheduler))
            .subscribe();
        active = false;
    }

    @Override
    public void replay(File audioFile) {
        if (active) return;
        speechRecognitionService.recognizeSpeech(audioFile)
            .doAfterSuccess(it -> logger.info("Recognized speech: " + it))
            .flatMapSingle(chatService::replay)
            .doAfterSuccess(it -> logger.info("Replay: " + it))
            .flatMapSingle(textToSpeechService::textToSpeechBufferedFile)
            .doAfterSuccess(it -> logger.info("File with voice response: " + it.getAbsolutePath()))
            .map(File::getAbsolutePath)
            .doAfterSuccess(it -> audioPlayerManager.loadItem(it, scheduler))
            .subscribe();
        active = false;
    }
}
