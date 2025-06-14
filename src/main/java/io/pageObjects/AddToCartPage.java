package io.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.utilites.Utilites;

public class AddToCartPage extends Utilites {

	WebDriver driver;

	public AddToCartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
	WebElement cartButton;

	@FindBy(xpath = "//div[@class='cartSection']/h3")
	List<WebElement> cartItems;

	@FindBy(css = "li[class='totalRow'] button")
	WebElement checkoutButton;

	@FindBy(xpath = "(//select[@class='input ddl'])[1]")
	WebElement month;

	@FindBy(xpath = "(//select[@class='input ddl'])[2]")
	WebElement date;
	
	@FindBy(css = "div[class='field small'] input[class='input txt']")
	WebElement cvv;
	
	@FindBy(css = "div[class='field'] input[class='input txt']")
	WebElement card;
	
	@FindBy(css = "input[name='coupon']")
	WebElement coupon;

	@FindBy(css=".btn.btn-primary.mt-1")
	WebElement applyButton;
	
	@FindBy(css = ".ng-animating")
	WebElement spinner;
	
	@FindBy(css="p[class='mt-1 ng-star-inserted']")
	WebElement validateCoupon;
	
	@FindBy(css="div[class='form-group'] input[class='input txt text-validated']")
	WebElement searchField;
	
	@FindBy(css="button[class='ta-item list-group-item ng-star-inserted']")
	List<WebElement> countries;
	
	@FindBy(css="a[class='btnn action__submit ng-star-inserted']")
	WebElement placeOrder;
	
	@FindBy(css=".hero-primary")
	WebElement confirmMsg;
	
	public boolean verifyAddToCart(String productName) throws InterruptedException {
		Thread.sleep(2000);
		cartButton.click();
		boolean match = cartItems.stream().anyMatch(cartItem -> cartItem.getText().equals(productName));
		if (match) {
			checkoutButton.click();
		} else {

			System.out.println("Something went wrong");
		}

		return match;

	}

	public void addToCartAllActions(String cvvNumber, String cardName, String couponCode) {

		singleDropdown(month, 8);
		singleDropdown(date, 26);
		cvv.sendKeys(cvvNumber);
		card.sendKeys(cardName);
		coupon.sendKeys(couponCode);
		applyButton.click();
		waitForElementToDisappear(spinner);
		System.out.println(validateCoupon.getText());
	}
	
	public String HandlingDynamicData(String name, String countryName) {
		searchField.sendKeys(name);
		countries.stream().filter(country -> country.getText().equalsIgnoreCase(countryName)).findAny()
		.ifPresentOrElse(country -> country
				.findElement(By.cssSelector("button[class='ta-item list-group-item ng-star-inserted'] i"))
				.click(), () -> {
					System.out.println("Country not found ");
				});
		placeOrder.click();
		return confirmMsg.getText();
	}
}
