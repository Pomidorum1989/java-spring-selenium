package io.dorum.tests;

import io.dorum.pages.LoginPage;
import io.dorum.pages.ProductPage;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static io.dorum.utils.RestAssuredUtils.fetchCredentials;

@Story("Sauce lab")
@Feature("Login mechanism")
public class LoginTest extends BaseTest {

    @DataProvider(name = "credentials", parallel = true)
    public Object[][] dataProvider() {
        Map<String, Object> credentialsMap = fetchCredentials();
        return credentialsMap.entrySet().stream()
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                .toArray(Object[][]::new);
    }

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private ProductPage productPage;

    @Value("${baseUrl}")
    private String baseUrl;

    @Severity(SeverityLevel.CRITICAL)
    @Description("Sauce lab login test")
    @Tags({@Tag("Demo"), @Tag("login")})
    @Owner("Pomidorum")
    @Test(description = "Login test", dataProvider = "credentials")
    public void testLogin(String login, String password) {
        loginPage.openURL(baseUrl);
        loginPage.login(login, password);
        Assert.assertTrue(productPage.isCartIconVisible(), "Cart icon isn't displayed");
        productPage.clickMenuBtn();
        productPage.clickLogOutBtn();
        Assert.assertEquals(loginPage.getLogoText(), "Swag Labs", "Incorrect logo text");
    }
}
