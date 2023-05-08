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

    @Override
    public void replay(byte[] audioData) {
        speechRecognitionService.recognizeSpeech(audioData)
            .doAfterSuccess(it -> logger.info("Recognized speech: " + it))
            .flatMap(chatService::replay)
            .doAfterSuccess(it -> logger.info("Replay: " + it))
            .flatMap(textToSpeechService::textToSpeechBufferedFile)
            .doAfterSuccess(it -> logger.info("File with voice response: " + it.getAbsolutePath()))
            .map(File::getAbsolutePath)
            .doAfterSuccess(it -> audioPlayerManager.loadItem(it, scheduler))
            .subscribe();
    }
}
