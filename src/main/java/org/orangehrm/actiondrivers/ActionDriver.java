package org.orangehrm.actiondrivers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        log.info("WebDriver instance is created!");
    }

    // Method to click an element
    public void click(By by) {
        String elementDescription = getElementDescription(by);
        try {
            waitForElementToBeClickable(by);
            driver.findElement(by).click();
            log.info("Clicked an element: "+elementDescription);
        } catch (Exception e) {
            log.error("Unable to click and element: {}", e.getMessage());
        }
    }

    // Method to enter text
    public void enterText(By by, String value) {
        waitForElementToBeVisible(by);
        WebElement element = driver.findElement(by);
        element.clear();
        element.sendKeys(value);
        log.info("Entered Text: {}", value);
    }

    // Method to get text from an input field
    public String getText(By by) {
        waitForElementToBeVisible(by);
        return driver.findElement(by).getText();
    }

    // Method to compare Text/Value
    public void compareText(By by, String expectedText) {
        waitForElementToBeVisible(by);
        String actualText = driver.findElement(by).getText();
        System.out.println(actualText.equals(expectedText) ? "Text are matching! " : "Text not matched!");
    }

    // Method to check if an element is displayed
    public boolean isDisplayed(By by) {
        waitForElementToBeVisible(by);
        return driver.findElement(by).isDisplayed();
    }

    // Wait for page load
    public void waitForPageLoad(int timeOutInSec) {
        wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(
                WebDriver -> ((JavascriptExecutor) WebDriver).executeScript(
                        "return document.readyState").equals("complete"));
    }

    // Scroll to an element
    public void scrollToElement(By by) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(by);
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Wait for element to be clickable
    private void waitForElementToBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            log.error("Unable to click an element: " + e.getMessage());
        }
    }

    // Wait for element to be visible
    private void waitForElementToBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            log.error("Element is not visible: " + e.getMessage());
        }
    }

    // Method to get the description of an element using By locator
    public String getElementDescription(By locator) {
        if (driver == null)
            return "driver is null";
        if (locator == null)
            return "locator is null";

        WebElement element = driver.findElement(locator);
        String submit = element.getDomAttribute("type");
        String id = element.getDomAttribute("id");
        String text = element.getText();
        String className = element.getDomAttribute("class");
        String placeholder = element.getDomAttribute("placeholder");

        // Return the description based on the element.
        if (!submit.isEmpty()) {
            return "Element with name: " + submit;
        } else if (!id.isEmpty()) {
            return "Element with id: " + id;
        } else if (!text.isEmpty()) {
            return "Element with text: " + text;
        } else if (!className.isEmpty()) {
            return "Element with className: " + className;
        } else if (!placeholder.isEmpty()) {
            return "Element with placeholder: " + placeholder;
        } return "";
    }
}










