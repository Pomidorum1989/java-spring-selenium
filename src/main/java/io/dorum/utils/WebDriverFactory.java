package io.dorum.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

@Log4j2
public class WebDriverFactory {

    public static WebDriver createDriver(String browser) {
        WebDriverManager webDriverManager;
        WebDriver driver = null;
        long threadId = Thread.currentThread().threadId();
        if ("chrome_docker".equalsIgnoreCase(browser)) {
            if (WebDriverManager.isDockerAvailable()) {
                webDriverManager = WebDriverManager.chromedriver().browserInDocker();
                webDriverManager.dockerDefaultArgs("--disable-gpu,--no-sandbox,--lang=en-US,--disable-gpu,--incognito");
                driver = webDriverManager.create();
                log.info("Browser container id: {}, docker server url:{}",
                        webDriverManager.getDockerBrowserContainerId(), webDriverManager.getDockerSeleniumServerUrl());
            } else {
                log.error("You need to start the Docker app");
            }
        } else if ("chrome".equalsIgnoreCase(browser)) {
            webDriverManager = WebDriverManager.chromedriver().cachePath("./build/chromedriver/" + threadId);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--lang=en-US");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--incognito");
            driver = webDriverManager.capabilities(chromeOptions).create();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            webDriverManager = WebDriverManager.firefoxdriver().cachePath("./build/firefoxdriver/" + threadId);
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            driver = webDriverManager.capabilities(firefoxOptions).create();
        } else if ("edge".equalsIgnoreCase(browser)) {
            webDriverManager = WebDriverManager.edgedriver().cachePath("./build/edgedriver/" + threadId);
            EdgeOptions edgeOptions = new EdgeOptions();
            driver = webDriverManager.capabilities(edgeOptions).create();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }
}
