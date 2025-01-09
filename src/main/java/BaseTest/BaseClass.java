package BaseTest;

import BusinessLogics.ExcelFileReadAndWrite;
import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class BaseClass {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Logger logger = LogManager.getLogger(BaseClass.class);
    public static Faker faker = new Faker();

    public static ExtentReports report;
    public static ExtentTest test;
    public String className = this.getClass().getSimpleName();
    public static Actions actions;
    public static Select select;
    protected static ExcelFileReadAndWrite.ExcelReader excelReader;

    /**
     * Setup browser based on the provided name.
     */
    public static void setupBrowser(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "msedge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser name not recognized: " + browserName);
        }
        driver.manage().window().maximize();
    }

    /**
     * Setup Extent Reports configuration.
     */
    @BeforeTest
    public void reportSetup() {
        try {
            System.setProperty("logFilename", className);

            String reportPath = System.getProperty("user.dir") + "//HTMLReports//ExtentReport//" + className + ".html";
            report = new ExtentReports(reportPath);
            report.addSystemInfo("HostName", "Hybrid Framework")
                    .addSystemInfo("Environment", "Practice OrangeHRM")
                    .addSystemInfo("User", "Nikhil")
                    .addSystemInfo("Project Name", "OrangeHRM");
            // Ensure the extent-config.xml exists
            String configFilePath = System.getProperty("user.dir") + "//extent-config.xml";
            File configFile = new File(configFilePath);
            if (configFile.exists()) {
                report.loadConfig(configFile);
            } else {
                logger.warn("Extent config file not found at: " + configFilePath);
            }
        } catch (Exception ex) {
            logger.error("Error during report setup: ", ex);
        }
    }

    /**
     * Capture and log test results after each test method.
     */
    @AfterMethod
    public void getReport(ITestResult result) {
        try {
            String screenshotPath = capture(result.getName());
            if (result.getStatus() == ITestResult.FAILURE) {
                test.log(LogStatus.FAIL, "Test Case Failed: " + result.getName());
                test.log(LogStatus.FAIL, "Error: " + result.getThrowable());
                test.log(LogStatus.FAIL, "Screenshot: " + test.addScreenCapture(screenshotPath));
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.log(LogStatus.PASS, "Test Case Passed: " + result.getName());
                test.log(LogStatus.PASS, "Screenshot: " + test.addScreenCapture(screenshotPath));
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.log(LogStatus.SKIP, "Test Case Skipped: " + result.getName());
            }
        } catch (Exception ex) {
            logger.error("Error during result reporting: ", ex);
        }
    }

    /**
     * Initialize ExcelReader before the test suite.
     */
    @BeforeSuite
    public void beforeSuite() throws IOException {
        String excelFilePath = "C://Users//nikhildhumane//IdeaProjects//ThruCareHealthCare//src//main//java//BusinessLogics//ExcelData//ThrucareData.xlsx";

        try {
            excelReader = new ExcelFileReadAndWrite.ExcelReader(excelFilePath);
            System.out.println("Excel file loaded successfully.");
        } catch (Exception e) {
            logger.error("Error loading Excel file: ", e);
            throw new IOException("Failed to initialize ExcelReader", e);
        }
    }

    /**
     * Cleanup after the test suite.
     */
    @AfterSuite
    public void afterSuite() {
        if (excelReader != null) {
            try {
                excelReader.close();
                System.out.println("Excel file closed successfully.");
            } catch (IOException e) {
                logger.error("Error closing Excel file: ", e);
            }
        }
    }

    /**
     * Close Extent Report and quit driver after all tests.
     */
    @AfterTest
    public void endTest() {
        try {
            if (test != null) {
                report.endTest(test);
            }
            if (report != null) {
                report.flush();
                report.close();
            }
        } catch (Exception ex) {
            logger.error("Exception while ending report: ", ex);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    /**
     * Capture a screenshot.
     */
    public String capture(String name) throws IOException {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "//Screenshots//" + className + "_" + name + ".png");
        FileUtils.copyFile(scrFile, dest);
        return dest.getAbsolutePath();
    }

    /**
     * Add log entries with optional screenshot and exception details.
     */
    public void stepLabel(LogStatus status, String message, String screenshotPath, Throwable throwable) {
        if (status == LogStatus.FAIL && throwable != null) {
            logger.error(message, throwable);
            test.log(status, message + " - Exception: " + throwable.getMessage());
        } else {
            logger.info(message);
            test.log(status, message);
        }
        if (screenshotPath != null) {
            test.log(status, "Screenshot: " + test.addScreenCapture(screenshotPath));
        }
    }
}
