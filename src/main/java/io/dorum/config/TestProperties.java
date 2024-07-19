package io.dorum.config;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class TestProperties {
    private String browser = "chrome";
    @NotBlank
    private String baseUrl;
}
