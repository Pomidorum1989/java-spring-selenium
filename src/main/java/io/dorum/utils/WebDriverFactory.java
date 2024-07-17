package io.dorum.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    public static WebDriver createDriver(String browser) {
        WebDriverManager webDriverManager;
        long threadId = Thread.currentThread().threadId();
        if ("chrome".equalsIgnoreCase(browser)) {
            webDriverManager = WebDriverManager.chromedriver().cachePath("./build/chromedriver/" + threadId);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--lang=en-US");
//                    chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--incognito");
            return webDriverManager.capabilities(chromeOptions).create();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            webDriverManager = WebDriverManager.firefoxdriver().cachePath("./build/firefoxdriver/" + threadId);
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            return webDriverManager.capabilities(firefoxOptions).create();
        } else if ("edge".equalsIgnoreCase(browser)) {
            webDriverManager = WebDriverManager.edgedriver().cachePath("./build/edgedriver/" + threadId);
            EdgeOptions edgeOptions = new EdgeOptions();
            return webDriverManager.capabilities(edgeOptions).create();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
