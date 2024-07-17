package io.dorum.pages;

import io.dorum.utils.WaitUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


@Slf4j
public abstract class BasePage {

    @Lazy
    @Autowired
    protected WaitUtils waitUtils;

    @Lazy
    @Autowired
    protected WebDriver driver;

    public void openURL(String url) {
        driver.get(url);
        log.info("Url {} is opened", url);
    }
}
