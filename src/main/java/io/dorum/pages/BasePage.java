package io.dorum.pages;

import io.dorum.utils.WaitUtils;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Log4j2
public abstract class BasePage {

    @Autowired
    protected WaitUtils waitUtils;

    @Lazy
    @Autowired
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openURL(String url) {
        driver.get(url);
        log.info("Url {} is opened", url);
    }
}
