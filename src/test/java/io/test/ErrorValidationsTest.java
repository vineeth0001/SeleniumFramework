package io.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.pageObjects.AddToCartPage;
import io.pageObjects.ProductCatalogue;
import io.testComponents.BaseTest;
import io.testComponents.RetryMechanism;

public class ErrorValidationsTest extends BaseTest {

	String expectedLoginPopup = "Login Successfully";
	String productName = "ZARA COAT 3";

	@Test(groups = "Handlingerrors")
	public void loginErrorValidations() {

		loginPage.LoginApp("vineeth@gmail.com", "Saanvi@115");

		Assert.assertEquals(loginPage.getErrorMsg(), "Incorrect email password.");
	}

	@Test

	public void productErrorValidation() throws InterruptedException {

		ProductCatalogue productCatalogue = loginPage.LoginApp("vineeth00@gmail.com", "Iamking@000");

		// Get all the products and provide condition based on productName and click it.
		// wait until the spinner gets disappear, print popUp Message.

		AddToCartPage addToCart = productCatalogue.addProductToCart(productName);

		// Click on Cart button, list the cartItems and Validate it.
		// Once it is validate click on checkout button.

		// AddToCartPage addToCart = new AddToCartPage(driver);
		boolean match = addToCart.verifyAddToCart("ZARA COAT");
		Assert.assertFalse(match);
	}
}
