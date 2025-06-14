package io.self;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsDemo {

	ExtentReports extent;

	@Test
	public void initialiseBrowser() {

		ExtentTest test = extent.createTest("intialize Browser");
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/");
		System.out.println(driver.getTitle());
		driver.close();
		test.fail("Results not Matched");
		extent.flush();
	}

	@BeforeTest
	public void extentReportsConfig() {

		File filepath = new File(System.getProperty("user.dir") + "\\reports\\index.html");
		ExtentSparkReporter reporter = new ExtentSparkReporter(filepath);
		reporter.config().setDocumentTitle("Test Results");
		reporter.config().setReportName("Web Automation Results");

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Vineeth");

	}

}
