package org.orangehrm.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> driverMap = new HashMap();

    // Initialize the Extent Report
    public static ExtentReports getExtentReporter() {
        if(extentReports == null) {
            String reportPath =
                    System.getProperty("user.dir")+"/src/test/resources/ExtentReport/ExtentReport.html";
            ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(reportPath);
            extentSparkReporter.config().setReportName("Automation Test Report");
            extentSparkReporter.config().setDocumentTitle("Orange HRM Report");
            extentSparkReporter.config().setTheme(Theme.DARK);

            extentReports = new ExtentReports();
            // adding system information
            extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        }
        return extentReports;
    }

    // Start the test
    public static ExtentTest startTest(String testName) {
        ExtentTest extentTest = getExtentReporter().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    // End a test
    public static void endTest() {
        getExtentReporter().flush();
    }

    // Get current thread's test
    public static ExtentTest getTest() {
        return test.get();
    }

    // Method to get the name of the current test
    public static String getTestName() {
        ExtentTest currentTest = getTest();
        return currentTest != null ? currentTest.getModel().getName() :
                "No test is currently active for this thread";
    }

    // Log a step
    public static void logStep(String logMessage) {
        getTest().info(logMessage);
    }

    // Log a validation with screenshot
    public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenshotMessage) throws IOException {
        getTest().pass(logMessage);
        // Screenshot method
        attachScreenshot(driver, screenshotMessage);
    }

    // Log a failure
    public static void logFailure(WebDriver driver, String logMessage, String screenshotMessage) throws IOException {
        getTest().fail(logMessage);
        // Screenshot method
        attachScreenshot(driver, screenshotMessage);
    }

    // Log a skip
    public static void logSkip(String logMessage) {
        getTest().skip(logMessage);
    }

    // Take a screenshot with date and time in file
    public static String takeScreenshot(WebDriver driver, String screenshotName) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
        // format time and date for file name
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        // saving a screenshot
        String destinationPath =
                System.getProperty("user.dir")+"/src/test/resources/ExtentReport/Screenshots"
                        +screenshotName+"_"+timestamp+".png";
        File finalPath = new File(destinationPath);
        FileUtils.copyFile(src, finalPath);

        // convert screenshot to Base64 for embedding in the Report
        String base64Format = convertToBase64(src);
        return base64Format;
    }

    // Convert screenshot to Base64 format
    public static String convertToBase64(File screenshotFile) throws IOException {
        String base64Format = "";
        // Read the file content into a byte array
        byte[]  fileContent = FileUtils.readFileToByteArray(screenshotFile);
        // convert the byte array to Base64 string
        base64Format = Base64.getEncoder().encodeToString(fileContent);
        return base64Format;
    }

    // Attach screenshot to report using Base64
    public static void attachScreenshot(WebDriver driver, String message) throws IOException {
        String screenshotBase64 = takeScreenshot(driver, getTestName());
        getTest().info(message, MediaEntityBuilder
                .createScreenCaptureFromBase64String(screenshotBase64).build());
    }

    // Register WebDriver for current thread
    public static void registerDriver(WebDriver driver) {
        driverMap.put(Thread.currentThread().getId(), driver);
    }
}