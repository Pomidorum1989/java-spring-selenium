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
public class SpringTestConfig {

    @Autowired
    private Environment env;

    @Bean
    @Scope("singleton")
    public ThreadLocal<WebDriver> threadlocal() {
        return new ThreadLocal<>();
    }

    @Bean(destroyMethod = "quit")
    @Scope("prototype")
    public WebDriver webDriver(ThreadLocal<WebDriver> threadLocal) {
        if (Objects.isNull(threadLocal.get())) {
            log.info("Web driver {} instance was created", Thread.currentThread().threadId());
            threadLocal.set(WebDriverFactory.createDriver(env.getProperty("browser")));
        }
        return threadLocal.get();
    }
}
