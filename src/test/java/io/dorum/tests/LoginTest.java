package io.dorum.tests;

import io.dorum.pages.LoginPage;
import io.dorum.pages.ProductPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "credentials", parallel = true)
    public Object[][] dataProvider() {
        return new Object[][]{
                new String[]{"standard_user", "secret_sauce"},
                new String[]{"locked_out_user", "secret_sauce"},
                new String[]{"problem_user", "secret_sauce"},
                new String[]{"performance_glitch_user", "secret_sauce"},
                new String[]{"error_user", "secret_sauce"},
                new String[]{"visual_user", "secret_sauce"}
        };
    }

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private ProductPage productPage;

    @Value("${baseUrl}")
    private String baseUrl;

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
