package krystian.kryszczak.discord.bot.http.openai;

import io.micronaut.http.client.multipart.MultipartBody;
import krystian.kryszczak.discord.bot.model.openai.completion.message.Message;
import krystian.kryszczak.discord.bot.model.openai.completion.request.ChatCompletionRequestBody;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.stream.Collectors;

import static fixture.util.WireMockUtils.wireMockEnv;
import static org.junit.jupiter.api.Assertions.*;

public final class ReactorOpenAIHttpClientTest {
    @Test
    public void createTranscription() {
        wireMockEnv(context -> {
            final ReactorOpenAIHttpClient httpClient = context.getBean(ReactorOpenAIHttpClient.class);

            final File file = new File("src/test/resources/voices/Hello world!.mp3");
            assertTrue(file.isFile());
            assertTrue(file.length() > 0);

            final String transcription = httpClient.createTranscription(
                MultipartBody.builder()
                    .addPart("file", file)
                    .addPart("model", "whisper-1")
                    .addPart("response_format", "text")
                    .build()
            ).block();

            assertNotNull(transcription);
            assertFalse(transcription.isBlank());
            assertTrue(transcription.matches("(?i).*hello world.*"));
        });
    }

    @Test
    public void createChatCompletion() {
        wireMockEnv(context -> {
            final ReactorOpenAIHttpClient httpClient = context.getBean(ReactorOpenAIHttpClient.class);

            final ChatCompletionRequestBody requestBody = ChatCompletionRequestBody.builder()
                .model("gpt-3.5-turbo")
                .messages(new Message[] {
                    Message.builder()
                        .role("system")
                        .content("You are a helpful assistant.")
                        .build(),
                    Message.builder()
                        .role("user")
                        .content("Hello!")
                        .build(),
                }).build();

            final String response = httpClient.createChatCompletion(requestBody)
                .collect(Collectors.joining())
                .block();

            assertNotNull(response);
            assertFalse(response.isBlank());
            assertTrue(response.matches("(?i).*Hello there, how may I assist you today\\?.*"));
        });
    }
}
