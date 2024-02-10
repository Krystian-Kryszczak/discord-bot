package fixture.util;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public final class WireMockUtils {
    private static final WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:3.3.1-alpine")
        .withMappingFromResource("open-ai", "wiremock/stubs/openai.com-stubs.json");

    @Contract(" -> new")
    private static @NotNull Map<String, Object> getProperties() {
        return Collections.singletonMap("micronaut.http.services.open-ai.url", WireMockUtils.wiremockServer.getBaseUrl());
    }

    public static void wireMockEnv(final @NotNull Consumer<ApplicationContext> contextConsumer) {
        wiremockServer.start();
        try (final EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, getProperties())) {
            contextConsumer.accept(server.getApplicationContext());
        }
    };
}
