package io.resources;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsNG {

	public static ExtentReports getReportsObject() {

		File filepath = new File(System.getProperty("user.dir") + "\\reports\\TestExecutionReport.html");
		ExtentSparkReporter reporter = new ExtentSparkReporter(filepath);
		reporter.config().setDocumentTitle("Framework Results");
		reporter.config().setReportName("Web Automation Results");

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Vineeth");
		
		return extent;
	}
}
