package krystian.kryszczak.http.elevenlabs;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.rxjava3.core.Observable;
import krystian.kryszczak.model.elevenlabs.TextToSpeech;

@Client(id = "eleven-labs")
@Header(name = "xi-api-key", value = "${eleven-labs.token}")
public interface ElevenLabsRxHttpClient {
    @Post("/v1/text-to-speech/${eleven-labs.default-voice-id}/stream")
    HttpResponse<Observable<byte[]>> textToSpeechStream(@Body TextToSpeech body);

    @Post("/v1/text-to-speech/{voice_id}/stream")
    HttpResponse<Observable<byte[]>> textToSpeechStream(@Body TextToSpeech body, @NonNull String voice_id);
}
