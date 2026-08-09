package org.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.orangehrm.actiondrivers.ActionDriver;

public class LoginPage {

    private ActionDriver actionDriver;
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginBtn = By.xpath("//button[@type='submit']");
    private By errorMessage = By.xpath("//p[normalize-space()='Invalid credentials']");

    public LoginPage(WebDriver driver) {
        this.actionDriver = new ActionDriver(driver);
    }

    // Method to perform login
    public void login(String username, String password) {
        actionDriver.enterText(usernameField, username);
        actionDriver.enterText(passwordField, password);
        actionDriver.click(loginBtn);
    }

    // Method to check if error message is displayed
    public boolean isErrorMessageDisplayed() {
        return actionDriver.isDisplayed(errorMessage);
    }

    // Method to verify error Message
    public String getErrorMessage() {
        return actionDriver.getText(errorMessage);
    }
}
