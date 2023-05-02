package krystian.kryszczak.model.audio.receiver;

import discord4j.voice.AudioReceiver;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BasicAudioReceiver extends AudioReceiver {
    private static final Logger logger = LoggerFactory.getLogger(BasicAudioReceiver.class);

    @Override
    public void receive(char sequence, int timestamp, int ssrc, byte[] audio) {
        logger.debug("Received new audio!");
    }
}
