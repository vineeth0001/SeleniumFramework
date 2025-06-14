package io.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.pageObjects.LoginPage;

public class BaseTest {

	public WebDriver driver;
	String browserName;
	public LoginPage loginPage;

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File src = screenshot.getScreenshotAs(OutputType.FILE);
		File dest = new File(
				System.getProperty("user.dir") + File.separator + "reports" + File.separator + testCaseName + ".png");
		FileUtils.copyFile(src, dest);
		return System.getProperty("user.dir") + File.separator + "reports" + File.separator + testCaseName + ".png";

	}

	public WebDriver setUp() throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\io\\resources\\Configurations.properties");
		prop.load(fis);
		browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		// browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {

			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", ".\\browsers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("Edge")) {

			driver = new EdgeDriver();
		} else {

			System.out.println("Invalid browser name");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		return driver;
	}

	@BeforeMethod(alwaysRun = true)
	public LoginPage launchApplication() throws IOException {
		driver = setUp();
		loginPage = new LoginPage(driver);
		loginPage.goTo();
		return loginPage;

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {

		if (driver != null) {
			driver.close();
		}
	}

	public List<HashMap<String, String>> dataReader(File filePath) throws IOException {

		String jsonContent = FileUtils.readFileToString(filePath, "UTF-8"); // ✅ Apache Commons IO
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

}
