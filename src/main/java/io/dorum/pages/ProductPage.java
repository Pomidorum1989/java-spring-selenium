package io.dorum.pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ProductPage extends BasePage {

    private final By cartIcon = By.id("shopping_cart_container");
    private final By menuBtn = By.id("react-burger-menu-btn");
    private final By logOutBtn = By.id("logout_sidebar_link");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartIconVisible() {
        return waitUtils.waitForElementToBeVisible(cartIcon).isDisplayed();
    }

    public void clickMenuBtn() {
        waitUtils.waitForElementToBeClickable(menuBtn).click();
        log.info("Clicked menu button");
    }

    public void clickLogOutBtn() {
        waitUtils.waitForElementToBeClickable(logOutBtn).click();
        log.info("Clicked logout button");
    }
}
