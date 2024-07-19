package io.dorum.utils;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@AllArgsConstructor
public class WaitUtils {

    private ObjectProvider<WebDriver> driverProvider;

    private FluentWait<WebDriver> createFluentWait() {
        return new FluentWait<>(driverProvider.getObject())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(Exception.class);
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return createFluentWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return createFluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return createFluentWait().until(ExpectedConditions.visibilityOf(element));
    }

    public boolean waitForElementToBeInvisible(By locator) {
        return createFluentWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBePresent(By locator) {
        return createFluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean waitForTextToBePresentInElement(By locator, String text) {
        return createFluentWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public boolean waitForUrlToContain(String fraction) {
        return createFluentWait().until(ExpectedConditions.urlContains(fraction));
    }

    public boolean waitForTitleToBe(String title) {
        return createFluentWait().until(ExpectedConditions.titleIs(title));
    }
}