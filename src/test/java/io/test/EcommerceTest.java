package io.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.pageObjects.AddToCartPage;
import io.pageObjects.OrdersPage;
import io.pageObjects.ProductCatalogue;
import io.testComponents.BaseTest;

public class EcommerceTest extends BaseTest {

	WebDriver driver;
	String expectedLoginPopup = "Login Successfully";
	String name = "Ind";
	String countryName = "India";
	String productName = "ZARA COAT 3";

	@Test(dataProvider = "TestData", groups = "Purchase")
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		// Enter Url, username, password and click on login button.

		ProductCatalogue productCatalogue = loginPage.LoginApp(input.get("email"), input.get("password"));

		// Get all the products and provide condition based on productName and click it.
		// wait until the spinner gets disappear, print popUp Message.

		AddToCartPage addToCart = productCatalogue.addProductToCart(input.get("product"));

		// Click on Cart button, list the cartItems and Validate it.
		// Once it is validate click on checkout button.

		// AddToCartPage addToCart = new AddToCartPage(driver);
		boolean match = addToCart.verifyAddToCart(input.get("product"));
		Assert.assertTrue(match);

		// Selecting the dropdown and all other actions

		addToCart.addToCartAllActions("438", "Vineeth", "rahulshettyacademy");

		// Handling Search bar, place order and validate the text

		String confirmMsg = addToCart.HandlingDynamicData(name, countryName);
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));

	}

	@Test(dependsOnMethods = "submitOrder")
	public void ordersHistory() {

		loginPage.LoginApp("vineeth@gmail.com", "Saanvi@143");
		OrdersPage orderPage = loginPage.goToOrders();
		boolean match = orderPage.verifyOrderDisplay(productName);
		Assert.assertTrue(match);
	}

	@DataProvider(name = "TestData")
	public Object[][] getData() throws IOException {

		File fis = new File(System.getProperty("user.dir") + "\\src\\main\\java\\io\\resources\\PurchaseOrder.json");
		List<HashMap<String, String>> data = dataReader(fis);

		return new Object[][] { { data.get(0) }, { data.get(1) } };

//		HashMap<String, String> data = new HashMap<String, String>();
//		data.put("email", "vineeth@gmail.com");
//		data.put("password", "Saanvi@143");
//		data.put("product", "ZARA COAT 3");
//
//		HashMap<String, String> data1 = new HashMap<String, String>();
//		data1.put("email", "vineeth00@gmail.com");
//		data1.put("password", "Iamking@000");
//		data1.put("product", "ADIDAS ORIGINAL");
//
//		return new Object[][] { { data }, { data1 } };
//		return new Object[][] { { "vineeth@gmail.com", "Saanvi@143", "ZARA COAT 3" },
//				{ "vineeth00@gmail.com", "Iamking@000", "ADIDAS ORIGINAL" } };

	}

}
