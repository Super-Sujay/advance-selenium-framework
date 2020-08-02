package org.automation.pages;

import static org.openqa.selenium.By.name;
import static org.openqa.selenium.By.xpath;

import org.automation.base.BasePage;
import org.automation.elements.Button;
import org.automation.elements.CheckBox;
import org.automation.elements.DropDown;
import org.automation.elements.TextBox;

public class NewtoursPurchasePage extends BasePage {

	public TextBox passenger1FirstName = new TextBox("Passenger 1 First Name", name("passFirst0"));
	public TextBox passenger1LastName = new TextBox("Passenger 1 Last Name", name("passLast0"));
	public DropDown passenger1Meal = new DropDown("Passenger 1 Meal", name("pass.0.meal"));
	public TextBox passenger2FirstName = new TextBox("Passenger 2 First Name", name("passFirst1"));
	public TextBox passenger2LastName = new TextBox("Passenger 2 Last Name", name("passLast1"));
	public DropDown passenger2Meal = new DropDown("Passenger 2 Meal", name("pass.1.meal"));
	public TextBox passenger3FirstName = new TextBox("Passenger 3 First Name", name("passFirst2"));
	public TextBox passenger3LastName = new TextBox("Passenger 3 Last Name", name("passLast2"));
	public DropDown passenger3Meal = new DropDown("Passenger 3 Meal", name("pass.2.meal"));
	public TextBox passenger4FirstName = new TextBox("Passenger 4 First Name", name("passFirst3"));
	public TextBox passenger4LastName = new TextBox("Passenger 4 Last Name", name("passLast3"));
	public DropDown passenger4Meal = new DropDown("Passenger 4 Meal", name("pass.3.meal"));
	public DropDown creditCardType = new DropDown("Credit Card Type", name("creditCard"));
	public TextBox creditCardNumber = new TextBox("Credit Card Number", name("creditnumber"));
	public DropDown creditCardExpirationMonth = new DropDown("Credit Card Expiration Month", name("cc_exp_dt_mn"));
	public DropDown creditCardExpirationYear = new DropDown("Credit Card Expiration Year", name("cc_exp_dt_yr"));
	public TextBox creditCardFirstName = new TextBox("Credit Card First Name", name("cc_frst_name"));
	public TextBox creditCardMiddleName = new TextBox("Credit Card Middle Name", name("cc_mid_name"));
	public TextBox creditCardLastName = new TextBox("Credit Card Last Name", name("cc_last_name"));
	public CheckBox ticketlessTravel = new CheckBox("Ticketless Travel", xpath(".//font[contains(text(),'Ticketless Travel')]//preceding-sibling::input"));
	public TextBox billingAddressLine1 = new TextBox("Billing Address 1", name("billAddress1"));
	public TextBox billingAddressLine2 = new TextBox("Billing Address 2", name("billAddress2"));
	public TextBox billingCity = new TextBox("Billing City", name("billCity"));
	public TextBox billingState = new TextBox("Billing State", name("billState"));
	public TextBox billingPostalCode = new TextBox("Billing Postal Code", name("billZip"));
	public DropDown billingCountry = new DropDown("Billing Country", name("billCountry"));
	public CheckBox sameAsBillingAddress = new CheckBox("Same As Billing Address", xpath(".//font[contains(text(),'Same as Billing Address')]//preceding-sibling::input"));
	public TextBox deliveryAddressLine1 = new TextBox("Delivery Address 1", name("delAddress1"));
	public TextBox deliveryAddressLine2 = new TextBox("Delivery Address 2", name("delAddress2"));
	public TextBox deliveryCity = new TextBox("Delivery City", name("delCity"));
	public TextBox deliveryState = new TextBox("Delivery State", name("delState"));
	public TextBox deliveryPostalCode = new TextBox("Delivery Postal Code", name("delZip"));
	public DropDown deliveryCountry = new DropDown("Delivery Country", name("delCountry"));
	public Button securePurchase = new Button("Secure Purchase", name("buyFlights"));

	public void enterPassenger1Details(String firstName, String lastName, String meal) {
		passenger1FirstName.enterText(firstName);
		passenger1LastName.enterText(lastName);
		passenger1Meal.selectByVisibleText(meal);
	}

	public void enterPassenger2Details(String firstName, String lastName, String meal) {
		passenger2FirstName.enterText(firstName);
		passenger2LastName.enterText(lastName);
		passenger2Meal.selectByVisibleText(meal);
	}

	public void enterPassenger3Details(String firstName, String lastName, String meal) {
		passenger3FirstName.enterText(firstName);
		passenger3LastName.enterText(lastName);
		passenger3Meal.selectByVisibleText(meal);
	}

	public void enterPassenger4Details(String firstName, String lastName, String meal) {
		passenger4FirstName.enterText(firstName);
		passenger4LastName.enterText(lastName);
		passenger4Meal.selectByVisibleText(meal);
	}

	public void enterCreditCardDetails(String cardType, String cardNumber, String expirationMonth, String expirationYear, String firstName, String middleName, String lastName) {
		creditCardType.selectByVisibleText(cardType);
		creditCardNumber.enterText(cardNumber);
		creditCardExpirationMonth.selectByVisibleText(expirationMonth);
		creditCardExpirationYear.selectByValue(expirationYear);
		creditCardFirstName.enterText(firstName);
		creditCardMiddleName.enterText(middleName);
		creditCardLastName.enterText(lastName);
	}

	public void enterBillingAddress(String addressLine1, String addressLine2, String city, String state, String postalCode, String country) {
		billingAddressLine1.enterText(addressLine1);
		billingAddressLine2.enterText(addressLine2);
		billingCity.enterText(city);
		billingState.enterText(state);
		billingPostalCode.enterText(postalCode);
		billingCountry.selectByVisibleText(country);
	}

	public void enterDeliveryAddress(String addressLine1, String addressLine2, String city, String state, String postalCode, String country) {
		deliveryAddressLine1.enterText(addressLine1);
		deliveryAddressLine2.enterText(addressLine2);
		deliveryCity.enterText(city);
		deliveryState.enterText(state);
		deliveryPostalCode.enterText(postalCode);
		deliveryCountry.selectByVisibleText(country);
	}

}
