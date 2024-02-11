package krystian.kryszczak.discord.bot.http.openai;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.multipart.MultipartBody;
import reactor.core.publisher.Mono;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Client(id = "open-ai")
@Header(name = AUTHORIZATION, value = "Bearer ${open-ai.token}")
public interface ReactorOpenAIHttpClient {
    @Post(value = "/v1/audio/transcriptions", produces = MediaType.MULTIPART_FORM_DATA)
    Mono<String> createTranscription(@Body MultipartBody body);
}
