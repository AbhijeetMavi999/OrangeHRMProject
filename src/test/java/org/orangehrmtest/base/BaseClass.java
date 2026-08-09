package org.orangehrmtest.base;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.orangehrm.utilities.ExtentManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@Slf4j
public class BaseClass {

    @Getter
    protected static Properties properties;
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public static void setProperties(Properties properties) {
        BaseClass.properties = properties;
    }

    @BeforeSuite
    public void loadConfig() throws IOException {
        // Load the configuration file
        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(
                "/home/abhijeet-mavi/IdeaProjects/OrangeHRMProject/src/main/resources/config.properties"
        );
        properties.load(fileInputStream);
        log.info("config.properties file loaded");

        // Start the Extent Report
        ExtentManager.getExtentReporter();
    }

    @BeforeMethod
    public void setUp() throws IOException {
        launchBrowser();
        configureBrowser();
        log.info("WebDriver initialize and Browser Maximized");
    }

    protected void launchBrowser() {
        // Initialize the WebDriver based on browser defined in config.properties
        String browser = properties.getProperty("browser");
        switch(browser) {
            case "chrome" : driver = new ChromeDriver();
            log.info("Chrome instance is created!");
            break;
            case "firefox" : driver = new FirefoxDriver();
            log.info("Firefox browser instance is created!");
            break;
            case "edge" : driver = new EdgeDriver();
            log.info("Edge browser instance is created!");
            break;
            default:
                log.warn("Unsupported Browser: {}", browser);
                throw new IllegalArgumentException("Unsupported Browser: "+browser);
        }
    }

    protected void configureBrowser() {
        int implicitWait = Integer.parseInt(properties.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().window().maximize();
        // navigate to URL
        driver.get(properties.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }
}
