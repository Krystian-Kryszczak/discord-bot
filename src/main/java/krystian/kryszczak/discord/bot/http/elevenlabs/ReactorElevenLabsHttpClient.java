package krystian.kryszczak.discord.bot.http.elevenlabs;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import krystian.kryszczak.discord.bot.model.elevenlabs.TextToSpeech;
import reactor.core.publisher.Mono;

@Client(id = "eleven-labs")
@Header(name = "xi-api-key", value = "${eleven-labs.token}")
public interface ReactorElevenLabsHttpClient {
    @Post("/v1/text-to-speech/${eleven-labs.default-voice-id}")
    Mono<HttpResponse<byte[]>> textToSpeech(@Body TextToSpeech body);
    @Post("/v1/text-to-speech/{voice_id}")
    Mono<HttpResponse<byte[]>> textToSpeech(@Body TextToSpeech body, @NonNull String voice_id);

    @Post("/v1/text-to-speech/${eleven-labs.default-voice-id}/stream")
    Mono<HttpResponse<byte[]>> textToSpeechStream(@Body TextToSpeech body);
    @Post("/v1/text-to-speech/{voice_id}/stream")
    Mono<HttpResponse<byte[]>> textToSpeechStream(@Body TextToSpeech body, @NonNull String voice_id);
}
