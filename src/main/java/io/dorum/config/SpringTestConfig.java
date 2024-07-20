package io.dorum.config;

import io.dorum.utils.WebDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.context.support.SimpleThreadScope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "io.dorum")
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(TestProperties.class)
@Slf4j
public class SpringTestConfig {

    @Bean
    @Scope("thread")
    public WebDriver webDriver(TestProperties properties) {
        return WebDriverFactory.createDriver(properties.getBrowser());
    }

    @Bean
    public CustomScopeConfigurer threadScopeConfigure() {
        CustomScopeConfigurer configure = new CustomScopeConfigurer();
        Map<String, Object> newScopes = new HashMap<>();
        newScopes.put("thread", new SimpleThreadScope());
        configure.setScopes(newScopes);
        return configure;
    }
}
