package org.automation.tests;

import java.util.Map;

import org.automation.base.BaseTest;
import org.automation.pages.NewtoursPurchase2Page;
import org.automation.pages.NewtoursPurchasePage;
import org.automation.pages.NewtoursReservation2Page;
import org.automation.pages.NewtoursReservationPage;
import org.automation.pages.NewtoursWelcomePage;
import org.testng.annotations.Test;

public class NewtoursExecution3 extends BaseTest {

	@Test(dataProvider = "ExcelDataProvider")
	public void bookFlightTest3(Map<String, String> data) {
		NewtoursWelcomePage welcomePage = new NewtoursWelcomePage();
		NewtoursReservationPage reservationPage = welcomePage.signIn(getUsername(), getPassword());
		reservationPage.roundTrip.click();
		reservationPage.passengers.selectByValue(data.get("No of Passengers"));
		reservationPage.selectDepartingFrom(data.get("Departure Airport"), data.get("Departure Month"), data.get("Departure Date"));
		reservationPage.selectArrivingIn(data.get("Arrival Airport"), data.get("Arrival Month"), data.get("Arrival Date"));
		reservationPage.businessClass.click();
		reservationPage.airline.selectByVisibleText(data.get("Airline"));
		NewtoursReservation2Page reservation2Page = reservationPage.continueNext.click(NewtoursReservation2Page.class);
		reservation2Page.pangeaAirlines362.click();
		reservation2Page.unifiedAirlines633.click();
		NewtoursPurchasePage purchasePage = reservation2Page.continueNext.click(NewtoursPurchasePage.class);
		purchasePage.enterPassenger1Details(data.get("Passenger1 First Name"), data.get("Passenger1 Last Name"), data.get("Passenger1 Meal Type"));
		purchasePage.enterPassenger2Details(data.get("Passenger2 First Name"), data.get("Passenger2 Last Name"), data.get("Passenger2 Meal Type"));
		purchasePage.enterPassenger3Details(data.get("Passenger3 First Name"), data.get("Passenger3 Last Name"), data.get("Passenger3 Meal Type"));
		purchasePage.enterPassenger4Details(data.get("Passenger4 First Name"), data.get("Passenger4 Last Name"), data.get("Passenger4 Meal Type"));
		purchasePage.enterCreditCardDetails(data.get("Credit Card Type"), data.get("Credit Card Number"), data.get("Credit Card Expiration Month"), data.get("Credit Card Expiration Year"), data.get("Credit Card First Name"), data.get("Credit Card Middle Name"), data.get("Credit Card Last Name"));
		purchasePage.enterBillingAddress(data.get("Billing Address Line1"), data.get("Billing Address Line2"), data.get("Billing City"), data.get("Billing State"), data.get("Billing Postal Code"), data.get("Billing Country"));
		purchasePage.enterDeliveryAddress(data.get("Delivery Address Line1"), data.get("Delivery Address Line2"), data.get("Delivery City"), data.get("Delivery State"), data.get("Delivery Postal Code"), data.get("Delivery Country"));
		NewtoursPurchase2Page purchase2Page = purchasePage.securePurchase.click(NewtoursPurchase2Page.class);
		purchase2Page.logOut.click();
	}

}
