package io.dorum.tests;

import io.dorum.config.TestConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

@Log4j2
@ContextConfiguration(classes = TestConfig.class)
public class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    protected TestConfig testConfig;

    protected WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod(Method method) {
        ThreadContext.put("threadName", String.valueOf(Thread.currentThread().threadId()));
        log.info("Method {} is started", method.getName());
        webDriver = context.getBean(WebDriver.class);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method, ITestResult result) {
        if (!result.isSuccess()) {
            getPageScreenShoot("Failure_" + method.getName());
        }
        ThreadContext.clearAll();
        TestConfig.cleanUp();
        log.info("Method {} is finished", method.getName());
    }

    protected void getPageScreenShoot(String fileName) {
        try {
            String name = "/build/screenshots/" + fileName + ".png";
            File screenshotFile = ((TakesScreenshot) webDriver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            FileHandler.copy(screenshotFile, new File(name));
            log.info("Screenshot was created {}", name);
        } catch (IOException e) {
            log.error("Unable to take screenshot");
        }
    }
}
