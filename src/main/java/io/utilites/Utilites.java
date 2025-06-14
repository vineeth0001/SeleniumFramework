package io.utilites;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import io.pageObjects.OrdersPage;

public class Utilites {

	@FindBy(css = "button[routerlink='/dashboard/myorders']")
	WebElement ordersButton;

	public OrdersPage goToOrders() {

		ordersButton.click();
		OrdersPage orderPage = new OrdersPage(driver);
		return orderPage;
	}

	WebDriver driver;

	public Utilites(WebDriver driver) {

		this.driver = driver;

	}

	public void waitForWebElementToAppear(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void waitForElementToDisappear(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void singleDropdown(WebElement element, int value) {

		Select month = new Select(element);
		month.selectByIndex(value);
	}

	public String getProperty(String key) throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(".\\src\\main\\java\\io\\resources\\Configurations.properties");
		prop.load(fis);
		return prop.getProperty(key);

	}
	
	public String getScreenshot(String testCaseName) throws IOException {

		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File src = screenshot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
		Files.copy(src, dest);
		return System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";

	}

	
}
