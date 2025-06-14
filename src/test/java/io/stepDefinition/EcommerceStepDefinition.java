package io.stepDefinition;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.pageObjects.AddToCartPage;
import io.pageObjects.ProductCatalogue;
import io.testComponents.BaseTest;

public class EcommerceStepDefinition extends BaseTest {

	public ProductCatalogue productCatalogue;
	public AddToCartPage addToCart;
	public String confirmMsg;

	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() throws IOException {
		launchApplication();
	}

	@Given("the user enters username {string} and password {string}")
	public void the_user_enters_username_and_password(String username, String password) {
		productCatalogue = loginPage.LoginApp(username, password);
	}

	@When("the user adds the product {string} to the cart")
	public void the_user_adds_the_product_to_the_cart(String productName) throws InterruptedException

	{
		addToCart = productCatalogue.addProductToCart(productName);

		boolean match = addToCart.verifyAddToCart(productName);
		Assert.assertTrue(match);
	}

	@When("enters all required details like {string} and {string} on the checkout page")
	public void enters_all_required_details_like_and_on_the_checkout_page(String country, String selectCountry) {
		addToCart.addToCartAllActions("438", "Vineeth", "rahulshettyacademy");
		confirmMsg = addToCart.HandlingDynamicData(country, selectCountry);
	}

	@Then("the confirmation page should display {string}")

	public void the_confirmation_page_should_display(String expectedValue) {
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));
		driver.close();
	}

	@Then("it should display error message as {string}")
	public void it_should_display_error_message_as(String expected) {
		Assert.assertEquals(loginPage.getErrorMsg(), expected);
		driver.close();
	}
}
