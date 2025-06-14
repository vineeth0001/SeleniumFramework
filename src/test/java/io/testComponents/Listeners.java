package io.testComponents;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.resources.ExtentReportsNG;

public class Listeners extends BaseTest implements ITestListener {

	ExtentReports extent = ExtentReportsNG.getReportsObject();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().fail("Test Failed: " + result.getThrowable());
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		try {
			if (driver != null) {
	            String filepath = getScreenshot(result.getMethod().getMethodName(), driver);
	            test.get().addScreenCaptureFromPath(filepath, result.getMethod().getMethodName());
	        } else {
	            System.out.println("Driver is null, cannot capture screenshot.");
	        }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().skip("Test Skipped: " + result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		if (extent != null) {
			extent.flush(); // ✅ Flush the report at the end
		}
	}
}
