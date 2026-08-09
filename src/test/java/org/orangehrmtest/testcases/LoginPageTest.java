package org.orangehrmtest.testcases;

import org.orangehrm.pages.HomePage;
import org.orangehrm.pages.LoginPage;
import org.orangehrmtest.base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test
    public void verifyLoginTest() {
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after successful login!");
    }

    @Test
    public void inValidLoginTest() {
        loginPage.login("fjfd", "asdj");
        String expectedErrorMessage = "Invalid credentials";
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
