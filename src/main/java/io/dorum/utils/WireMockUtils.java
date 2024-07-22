package io.dorum.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Log4j2
public class WireMockUtils {

    public static WireMockServer startServer() {
        WireMockServer wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
        try {
            String responseBody = new String(Files.readAllBytes(Paths.get("src/test/resources/credentials.json")));
            stubFor(get(urlEqualTo("/api/credentials"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(responseBody)));
            log.info("Wiremock stub is activated");
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to read JSON file for WireMock response", e);
        }
        return wireMockServer;
    }
}
