package io.dorum.tests;

import com.google.common.collect.ImmutableMap;
import io.dorum.config.SpringTestConfig;
import io.dorum.utils.Config;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Objects;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

@Slf4j
@SpringBootTest
@ContextConfiguration(classes = SpringTestConfig.class)
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected ObjectProvider<WebDriver> driverProvider;


    @BeforeSuite
    public void beforeSuite() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", Objects.requireNonNull(Config.getBrowser()))
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
    }

    @BeforeMethod
    public void beforeMethod(ITestContext context, Method method) {
        ThreadContext.put("threadName", String.valueOf(Thread.currentThread().threadId()));
        log.info("Method {} from suite {} is started", method.getName(), context.getSuite().getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method, ITestResult result, ITestContext context) {
        if (!result.isSuccess()) {
            takeScreenShot("failure_" + method.getName());
        }
        ThreadContext.clearAll();
        driverProvider.getObject().quit();
        log.info("Method {} from suite {} is finished", method.getName(), context.getSuite().getName());
    }

    @Attachment(value = "{fileName}", type = "image/png", fileExtension = ".png")
    public byte[] takeScreenShot(String fileName) {
        String name = fileName.replaceAll("\\s", "");
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driverProvider.getObject());
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(String.format(System.getProperty("user.dir") + "/build/screenshots/%s.png", name));
            FileUtils.copyFile(srcFile, destFile);
            log.info("Screenshot {} was created", destFile.getPath());
            return Files.readAllBytes(destFile.toPath());
        } catch (WebDriverException | IOException e) {
            log.error("Unable to create screenshot: {}", e.getMessage());
        }
        return null;
    }
}
