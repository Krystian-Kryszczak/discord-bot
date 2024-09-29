package fixture.util;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.testcontainers.utility.MountableFile;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.util.Map;
import java.util.function.Consumer;

public final class WireMockUtils {
    private static final WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:3.3.1-alpine")
        .withMappingFromResource("open-ai", "wiremock/stubs/openai.com-stubs.json")
        .withMappingFromResource("eleven-labs", "wiremock/stubs/elevenlabs.io-stubs.json")
        .withCopyFileToContainer(MountableFile.forClasspathResource("voices"), "home/wiremock/__files/voices");

    @Contract(" -> new")
    private static @NotNull @Unmodifiable Map<String, Object> getProperties() {
        return Map.of(
            "micronaut.http.services.eleven-labs.url", WireMockUtils.wiremockServer.getBaseUrl(),
            "micronaut.http.services.open-ai.url", WireMockUtils.wiremockServer.getBaseUrl()
        );
    }

    public static void wireMockEnv(final @NotNull Consumer<ApplicationContext> contextConsumer) {
        wiremockServer.start();
        try (final EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, getProperties())) {
            contextConsumer.accept(server.getApplicationContext());
        }
    }
}
