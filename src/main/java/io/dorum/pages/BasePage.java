package io.dorum.pages;

import io.dorum.utils.WaitUtils;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.ObjectProvider;

@Slf4j
@AllArgsConstructor
public abstract class BasePage {

    protected WaitUtils waitUtils;
    protected ObjectProvider<WebDriver> driverProvider;

    @Step("Url is opened [{url}]")
    public void openURL(String url) {
        driverProvider.getObject().get(url);
        log.info("Url {} is opened", url);
    }
}
