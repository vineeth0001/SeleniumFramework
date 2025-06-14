package io.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.utilites.Utilites;

public class OrdersPage extends Utilites {

	WebDriver driver;

	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//tbody/tr/td[2]")
	List<WebElement> productNames;

	public boolean verifyOrderDisplay(String productName) {
		boolean match = productNames.stream().anyMatch(product -> product.getText().equals(productName));
		return match;

	}

}
