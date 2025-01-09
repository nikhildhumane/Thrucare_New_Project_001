package BusinessLogics.Listeners;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import BaseTest.BaseClass;
import java.io.File;
import java.io.IOException;

public class TestListener extends BaseClass implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        // Test starts, log the start
        ExtentReportsManager.getTest().log(LogStatus.INFO, "Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Test passed, log the success
        ExtentReportsManager.getTest().log(LogStatus.PASS, "Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Test failed, log the failure and capture screenshot
        ExtentReportsManager.getTest().log(LogStatus.FAIL, "Test failed: " + result.getName());
        ExtentReportsManager.getTest().log(LogStatus.FAIL, result.getThrowable());

        // Capture screenshot for failure
        String screenshotPath = captureScreenshot(result.getName());
        ExtentReportsManager.getTest().addScreenCapture(screenshotPath);

        // Log failure details
        ExtentReportsManager.getTest().log(LogStatus.FAIL, "Test failed with exception: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Test skipped, log the skipped
        ExtentReportsManager.getTest().log(LogStatus.SKIP, "Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Test failed but within success percentage
        ExtentReportsManager.getTest().log(LogStatus.WARNING, "Test failed but within success percentage: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        // Test suite starts
        ExtentReportsManager.startReport();
        ExtentReportsManager.getTest().log(LogStatus.INFO, "Test suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Test suite finished
        ExtentReportsManager.endReport();
        ExtentReportsManager.getTest().log(LogStatus.INFO, "Test suite finished: " + context.getName());
    }

    private String captureScreenshot(String testName) {
        WebDriver driver = BaseClass.driver;
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
        try {
            FileUtils.copyFile(screenshot, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
}
