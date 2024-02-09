package krystian.kryszczak.discord.bot.model.elevenlabs.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.List;

@Data
@Introspected
public final class ElevenLabsError {
    private final List<Details> detail;

    @Data
    @Introspected
    public static final class Details {
        private final Loc loc;
        private final String msg;
        private final String type;

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"text", "offset"})
        public record Loc(String text, int offset) {}
    }
}
