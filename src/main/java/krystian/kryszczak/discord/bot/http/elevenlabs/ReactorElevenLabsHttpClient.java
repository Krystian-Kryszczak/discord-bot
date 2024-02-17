package krystian.kryszczak.discord.bot.http.elevenlabs;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krystian.kryszczak.discord.bot.model.elevenlabs.TextToSpeech;
import reactor.core.publisher.Mono;

@Client(id = "eleven-labs")
@Header(name = "xi-api-key", value = "${eleven-labs.token}")
public interface ReactorElevenLabsHttpClient {
    @Post("/v1/text-to-speech/${eleven-labs.default-voice-id}?optimize_streaming_latency=3")
    Mono<HttpResponse<byte[]>> textToSpeech(@Body TextToSpeech body);
    @Post("/v1/text-to-speech/{voiceId}?optimize_streaming_latency={optimizeLatency}")
    Mono<HttpResponse<byte[]>> textToSpeech(@Body TextToSpeech body, @NotNull String voiceId, @Min(0) @Max(4) int optimizeLatency);

    @Post("/v1/text-to-speech/${eleven-labs.default-voice-id}/stream?optimize_streaming_latency=3")
    Mono<HttpResponse<byte[]>> textToSpeechStream(@Body TextToSpeech body);
    @Post("/v1/text-to-speech/{voiceId}/stream?optimize_streaming_latency={optimizeLatency}")
    Mono<HttpResponse<byte[]>> textToSpeechStream(@Body TextToSpeech body, @NotNull String voiceId, @Min(0) @Max(4) int optimizeLatency);
}
