package org.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.orangehrm.actiondrivers.ActionDriver;

public class HomePage {

    private ActionDriver actionDriver;
    private By adminTab = By.xpath("//span[normalize-space()='Admin']");

    public HomePage(WebDriver driver) {
        this.actionDriver = new ActionDriver(driver);
    }
    
    // Method to verify if adminTab is visible
    public boolean isAdminTabVisible() {
        return actionDriver.isDisplayed(adminTab);
    }
}
