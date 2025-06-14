package io.pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.utilites.Utilites;

public class LoginPage extends Utilites {

	WebDriver driver;

	public LoginPage(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userEmail")
	WebElement username;

	@FindBy(id = "userPassword")
	WebElement password;

	@FindBy(id = "login")
	WebElement loginButton;

	@FindBy(css = "div[class*='toast-message']")
	WebElement errorMsg;

	public ProductCatalogue LoginApp(String user, String pwd) {

		username.sendKeys(user);
		password.sendKeys(pwd);
		loginButton.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}

	public String getErrorMsg() {
		waitForWebElementToAppear(errorMsg);
		return errorMsg.getText();

	}

	public void goTo() throws IOException {

		driver.get(getProperty("URL"));
	}

}
