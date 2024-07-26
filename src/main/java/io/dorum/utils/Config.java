package io.dorum.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Log4j2
public class Config {
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to load properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            log.error("Unable to load config file");
        }
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getBrowser() {
        String browser = System.getProperty("browser");
        if (Objects.nonNull(browser)) {
            properties.setProperty("browser", browser);
        }
        return properties.getProperty("browser");
    }

    public static String getMockUrl() {
        return properties.getProperty("wiremockUrl");
    }
}
