Feature: Verify that a user can add a product to the cart and submit an order 

Background: 
	Given the user is on the login page 
	
Scenario Outline: Verify positive ecommerce flow 
	Given the user enters username "<name>" and password "<pwd>" 
	When the user adds the product "<productName>" to the cart 
	And enters all required details like "<country>" and "<countrySelection>" on the checkout page 
	Then the confirmation page should display "Thankyou for the order." 
	
	Examples: 
		| name               | pwd         | productName      | country |countrySelection|
		| vineeth@gmail.com  | Saanvi@143  | ZARA COAT 3      |	Ind	  |	  India		   |
		
		
Scenario Outline: Verify with invalid username and password 
	Given  the user enters username "<name>" and password "<pwd>" 
	Then it should display error message as "Incorrect email or password." 
	
	Examples: 
		| name               | pwd         | 
		| vineeth@gmail.com  | Saanvi@123  |