package io.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.utilites.Utilites;

public class ProductCatalogue extends Utilites {

	WebDriver driver;

	public ProductCatalogue(WebDriver driver) {
		// initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(css = ".mb-3")
	List<WebElement> products;

	@FindBy(css = ".ng-animating")
	WebElement spinner;

	@FindBy(css = ".mb-3")
	WebElement productsBy;

	@FindBy(css = "#toast-container")
	WebElement toastMsg;
	By addToCart = By.cssSelector(".card-body button:last-of-type");

	public List<WebElement> getProductList() {
		waitForWebElementToAppear(productsBy);
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		return prod;
	}

	public AddToCartPage addProductToCart(String productName) throws InterruptedException {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForWebElementToAppear(toastMsg);
		waitForElementToDisappear(spinner);
		AddToCartPage addToCart = new AddToCartPage(driver);
		return addToCart;

	}

}
