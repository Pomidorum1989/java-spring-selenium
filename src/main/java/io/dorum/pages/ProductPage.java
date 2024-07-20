package io.dorum.pages;

import io.dorum.utils.WaitUtils;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductPage extends BasePage {

    private final By cartIcon = By.id("shopping_cart_container");
    private final By menuBtn = By.id("react-burger-menu-btn");
    private final By logOutBtn = By.id("logout_sidebar_link");

    public ProductPage(WaitUtils waitUtils, ObjectProvider<WebDriver> driverProvider) {
        super(waitUtils, driverProvider);
    }

    public boolean isCartIconVisible() {
        return waitUtils.waitForElementToBeVisible(cartIcon).isDisplayed();
    }

    @Step("Clicked main menu button")
    public void clickMenuBtn() {
        waitUtils.waitForElementToBeClickable(menuBtn).click();
        log.info("Clicked menu button");
    }

    @Step("Clicked log out button")
    public void clickLogOutBtn() {
        waitUtils.waitForElementToBeClickable(logOutBtn).click();
        log.info("Clicked logout button");
    }
}
