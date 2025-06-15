package io.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.excelUtils.ExcelUtils;
import io.pageObjects.AddToCartPage;
import io.pageObjects.OrdersPage;
import io.pageObjects.ProductCatalogue;
import io.testComponents.BaseTest;

public class EcommerceTest extends BaseTest {

	WebDriver driver;
	String expectedLoginPopup = "Login Successfully";
	String name = "Ind";
	String countryName = "India";
	ExcelUtils excel = new ExcelUtils();
	// String productName = "ZARA COAT 3";

	@Test(dataProvider = "TestData", groups = "Purchase")
//	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException { This is for Json
	public void submitOrder(String username, String password, String productName)
			throws IOException, InterruptedException {

		// Enter Url, username, password and click on login button.

		ProductCatalogue productCatalogue = loginPage.LoginApp(username, password);

		// Get all the products and provide condition based on productName and click it.
		// wait until the spinner gets disappear, print popUp Message.

		AddToCartPage addToCart = productCatalogue.addProductToCart(productName);

		// Click on Cart button, list the cartItems and Validate it.
		// Once it is validate click on checkout button.

		// AddToCartPage addToCart = new AddToCartPage(driver);
		boolean match = addToCart.verifyAddToCart(productName);
		Assert.assertTrue(match);

		// Selecting the dropdown and all other actions

		addToCart.addToCartAllActions("438", "Vineeth", "rahulshettyacademy");

		// Handling Search bar, place order and validate the text

		String confirmMsg = addToCart.HandlingDynamicData(name, countryName);
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));

	}

	@Test(dependsOnMethods = "submitOrder", dataProvider = "TestData")
	public void ordersHistory(String username, String password, String productName) {

		loginPage.LoginApp(username, password);
		OrdersPage orderPage = loginPage.goToOrders();
		boolean match = orderPage.verifyOrderDisplay(productName);
		Assert.assertTrue(match);
	}

	@DataProvider(name = "TestData")
	public Object[][] getData() throws IOException {

		Object[][] data = excel.getTestData(System.getProperty("user.dir") + "\\Excel\\testData.xlsx", "Credentails");
		return data;

		/*
		 * File fis = new File(System.getProperty("user.dir") +
		 * "\\src\\main\\java\\io\\resources\\PurchaseOrder.json"); List<HashMap<String,
		 * String>> data = dataReader(fis);
		 * 
		 * return new Object[][] { { data.get(0) }, { data.get(1) } };
		 */ // This is driving data from Json file

		/*
		 * HashMap<String, String> data = new HashMap<String, String>();
		 * data.put("email", "vineeth@gmail.com"); data.put("password", "Saanvi@143");
		 * data.put("product", "ZARA COAT 3");
		 * 
		 * HashMap<String, String> data1 = new HashMap<String, String>();
		 * data1.put("email", "vineeth00@gmail.com"); data1.put("password",
		 * "Iamking@000"); data1.put("product", "ADIDAS ORIGINAL");
		 * 
		 * return new Object[][] { { data }, { data1 } }; return new Object[][] { {
		 * "vineeth@gmail.com", "Saanvi@143", "ZARA COAT 3" }, { "vineeth00@gmail.com",
		 * "Iamking@000", "ADIDAS ORIGINAL" } };
		 */

	}

}
