package io.self;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Ecommerce {

	static WebDriver driver;
	static String expectedLoginPopup = "Login Successfully";

	public static void main(String args[]) throws InterruptedException {
		String productName = "ZARA COAT 3";
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/client");

		// Username and Password code
		driver.findElement(By.id("userEmail")).sendKeys("vineeth@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Saanvi@143");
		driver.findElement(By.id("login")).click();

		WebElement popUp = driver.findElement(By.cssSelector("#toast-container"));
		System.out.println(popUp.getText());
		Assert.assertEquals(popUp.getText(), expectedLoginPopup);

		List<WebElement> products = driver.findElements(By.xpath("//div[@class='card-body']/h5"));
		products.stream().filter(product -> product.getText().equals(productName)).findFirst().ifPresentOrElse(
				product -> product.findElement(By.xpath("//button[@class='btn w-10 rounded']")).click(), () -> {
					System.out.println("Product not found!");
				});

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		System.out.println(popUp.getText());
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

		List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
		boolean match = cartItems.stream().anyMatch(cartItem -> cartItem.getText().equals(productName));
		Assert.assertTrue(match);

		driver.findElement(By.cssSelector("li[class='totalRow'] button")).click();

		WebElement monthDropdown = driver.findElement(By.xpath("(//select[@class='input ddl'])[1]"));
		Select month = new Select(monthDropdown);
		month.selectByIndex(8);

		WebElement dateDropdown = driver.findElement(By.xpath("(//select[@class='input ddl'])[2]"));
		Select date = new Select(dateDropdown);
		date.selectByIndex(26);

		driver.findElement(By.cssSelector("div[class='field small'] input[class='input txt']")).sendKeys("438");
		driver.findElement(By.cssSelector("div[class='field'] input[class='input txt']")).sendKeys("Vineeth");
		driver.findElement(By.cssSelector("input[name='coupon']")).sendKeys("rahulshettyacademy");
		driver.findElement(By.cssSelector(".btn.btn-primary.mt-1")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		System.out.println(driver.findElement(By.cssSelector("p[class='mt-1 ng-star-inserted']")).getText());

		driver.findElement(By.cssSelector("div[class='form-group'] input[class='input txt text-validated']"))
				.sendKeys("Ind");

		List<WebElement> countries = driver
				.findElements(By.cssSelector("button[class='ta-item list-group-item ng-star-inserted']"));
		countries.stream().filter(country -> country.getText().equalsIgnoreCase("India")).findAny()
				.ifPresentOrElse(country -> country
						.findElement(By.cssSelector("button[class='ta-item list-group-item ng-star-inserted'] i"))
						.click(), () -> {
							System.out.println("Country not found ");
						});
		driver.findElement(By.cssSelector("a[class='btnn action__submit ng-star-inserted']")).click();
		String confirmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();

		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));

		driver.close();
	}

}
