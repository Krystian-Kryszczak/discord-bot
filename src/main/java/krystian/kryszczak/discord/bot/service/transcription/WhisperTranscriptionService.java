package krystian.kryszczak.discord.bot.service.transcription;

import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.audio.translator.WhisperTranslatorFactory;
import ai.djl.inference.Predictor;
import ai.djl.modality.audio.Audio;
import ai.djl.modality.audio.AudioFactory;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import org.bytedeco.ffmpeg.global.avutil;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;

@Singleton
public final class WhisperTranscriptionService implements TranscriptionService, AutoCloseable {
    private final ZooModel<Audio, String> model;
    private final Predictor<Audio, String> predictor;
    private final NDManager ndManager;
    private final AudioFactory audioFactory;

    public WhisperTranscriptionService() throws ModelNotFoundException, MalformedModelException, IOException {
        final var criteria = Criteria.builder()
            .setTypes(Audio.class, String.class)
            .optModelUrls("https://resources.djl.ai/demo/pytorch/whisper/whisper_en.zip")
            .optEngine("PyTorch")
            .optDevice(Device.cpu())
            .optTranslatorFactory(new WhisperTranslatorFactory())
            .build();

        this.model = criteria.loadModel();
        this.predictor = model.newPredictor();
        this.ndManager = model.getNDManager().newSubManager();

        this.audioFactory = AudioFactory.newInstance()
            .setChannels(1)
            .setSampleRate(16000)
            .setSampleFormat(avutil.AV_SAMPLE_FMT_S16);
    }

    @PreDestroy
    @Override
    public void close() {
        model.close();
        predictor.close();
        ndManager.close();
    }

    private @NotNull Mono<String> createTranscription(@NotNull Audio audio) {
        return Mono.fromCallable(() -> predictor.predict(audio)
            .replaceAll("<\\|startoftranscript\\|><\\|en\\|><\\|transcribe\\|><\\|notimestamps\\|>|<\\|endoftext\\|>", "")
            .trim()
        );
    }

    @Override
    public @NotNull Mono<String> createTranscription(byte @NotNull [] wavAudioData) {
        return createTranscription(
            audioFactory.fromNDArray(
                NDArray.decode(
                    ndManager,
                    wavAudioData
                )
            )
        );
    }

    @SneakyThrows
    @Override
    public @NotNull Mono<String> createTranscription(@NotNull File wavFile) {
        return createTranscription(audioFactory.fromFile(wavFile.toPath()));
    }
}
