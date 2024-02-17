package krystian.kryszczak.discord.bot.model.openai.tts;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serdeable
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TextToSpeechRequest(
    @NotNull String model,
    @NotNull String input,
    @NotNull String voice,
    @Nullable String responseFormat,
    @Nullable Float speed
) {
    public static final String TTS_1_MODEL = "tts-1";
    public static final String TTS_1_HD_MODEL = "tts-1";

    public static final class Builder {
        private @NotNull String model;
        private String input;
        private @NotNull String voice;
        private @Nullable String responseFormat;
        private @Nullable Float speed;

        public Builder() {
            this.model = "tts-1";
            this.voice = "alloy";
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder useTTS1Model() {
            this.model = "tts-1";
            return this;
        }

        public Builder useTTS1HDModel() {
            this.model = "tts-1-hd";
            return this;
        }

        public Builder setInput(String input) {
            this.input = input;
            return this;
        }

        public Builder setVoice(String voice) {
            this.voice = voice;
            return this;
        }

        public Builder useAlloyVoice() {
            this.voice = "alloy";
            return this;
        }

        public Builder useEchoVoice() {
            this.voice = "echo";
            return this;
        }

        public Builder useFableVoice() {
            this.voice = "fable";
            return this;
        }

        public Builder useOnyxVoice() {
            this.voice = "onyx";
            return this;
        }

        public Builder useNovaVoice() {
            this.voice = "nova";
            return this;
        }

        public Builder useShimmerVoice() {
            this.voice = "shimmer";
            return this;
        }

        public Builder setResponseFormat(String responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        public Builder useMp3ResponseFormat() {
            this.responseFormat = "mp3";
            return this;
        }

        public Builder useOpus3ResponseFormat() {
            this.responseFormat = "opus";
            return this;
        }

        public Builder useAacResponseFormat() {
            this.responseFormat = "aac";
            return this;
        }

        public Builder useFlacResponseFormat() {
            this.responseFormat = "flac";
            return this;
        }

        public Builder setSpeed(Float speed) {
            this.speed = Math.max(0.25f, Math.min(4.0f, speed));
            return this;
        }

        @Contract(" -> new")
        public @NotNull TextToSpeechRequest build() {
            if (this.input == null) throw new IllegalArgumentException("The input value must not be null!");
            if (this.input.length() > 4096) throw new IllegalArgumentException("The input value is too long! (max 4096 characters)");
            return new TextToSpeechRequest(model, input, voice, responseFormat, speed);
        }
    }

    @Override
    public @Nullable Float speed() {
        if (this.speed == null) return null;
        return Math.max(0.25f, Math.min(4.0f, this.speed));
    }
}
