package io.dorum.config;

import io.dorum.utils.WebDriverFactory;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@ComponentScan(basePackages = "io.dorum")
@PropertySource("classpath:application.properties")
@Log4j2
public class TestConfig {

    @Autowired
    private Environment env;

    private static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    @Bean
    @Scope("prototype")
    public WebDriver webDriver() {
        if (Objects.isNull(WEB_DRIVER_THREAD_LOCAL.get())) {
            log.info("Web driver {} instance was created", Thread.currentThread().threadId());
            WEB_DRIVER_THREAD_LOCAL.set(WebDriverFactory.createDriver(env.getProperty("browser")));
        }
        return WEB_DRIVER_THREAD_LOCAL.get();
    }

    public static void cleanUp() {
        if (Objects.nonNull(WEB_DRIVER_THREAD_LOCAL.get())) {
            WEB_DRIVER_THREAD_LOCAL.get().quit();
            WEB_DRIVER_THREAD_LOCAL.remove();
            log.info("Web driver {} instance was removed", Thread.currentThread().threadId());
        }
    }
}
