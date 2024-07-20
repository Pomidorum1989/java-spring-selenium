package io.dorum.pages;

import io.dorum.utils.WaitUtils;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.xpath("//h3[@data-test='error']");
    private final By logo = By.xpath("//div[@class = 'login_logo']");

    public LoginPage(WaitUtils waitUtils, ObjectProvider<WebDriver> driverProvider) {
        super(waitUtils, driverProvider);
    }

    @Step("Entered user name {username}")
    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
         driverProvider.getObject().findElement(usernameField).sendKeys(username);
    }

    @Step("Entered password {password}")
    public void enterPassword(String password) {
        log.info("Entering password");
         driverProvider.getObject().findElement(passwordField).sendKeys(password);
    }

    @Step("Clicked login button")
    public void clickLogin() {
        log.info("Clicking login button");
         driverProvider.getObject().findElement(loginButton).click();
    }

    @Step("Starting logging")
    public void login(String username, String password) {
        log.info("Logging in with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        getErrorMessageText();
    }

    public String getErrorMessageText() {
        try {
            WebElement webElement =  driverProvider.getObject().findElement(errorMessage);
            if (webElement.isDisplayed()) {
                String text = webElement.getText();
                log.info("Error message: {}", text);
                return text;
            }
        } catch (NoSuchElementException e) {
            log.warn("Error message didn't appear");
        }
        return "";
    }

    public String getLogoText() {
        return  driverProvider.getObject().findElement(logo).getText();
    }
}