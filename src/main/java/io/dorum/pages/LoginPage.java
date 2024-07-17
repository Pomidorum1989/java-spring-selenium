package io.dorum.pages;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.xpath("//h3[@data-test='error']");
    private final By logo = By.xpath("//div[@class = 'login_logo']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        log.info("Entering password");
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        log.info("Clicking login button");
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        log.info("Logging in with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        getErrorMessageText();
    }

    public String getErrorMessageText() {
        try {
            WebElement webElement = driver.findElement(errorMessage);
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
        return driver.findElement(logo).getText();
    }
}