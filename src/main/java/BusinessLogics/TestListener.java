package BusinessLogics;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());
        // You can capture screenshots here if needed
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // This method is called only if the test has failed but within the success percentage.
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test started in context: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test finished in context: " + context.getName());
    }
}

