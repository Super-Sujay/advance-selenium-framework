package org.automation.tests;

import org.automation.base.BaseTest;
import org.automation.pages.NewtoursPurchase2Page;
import org.automation.pages.NewtoursPurchasePage;
import org.automation.pages.NewtoursReservation2Page;
import org.automation.pages.NewtoursReservationPage;
import org.automation.pages.NewtoursWelcomePage;
import org.testng.annotations.Test;

public class NewtoursExecution extends BaseTest {

	@Test
	public void bookFlightTest1() {
		NewtoursWelcomePage welcomePage = new NewtoursWelcomePage();
		NewtoursReservationPage reservationPage = welcomePage.signIn(getUsername(), getPassword());
		reservationPage.roundTrip.click();
		reservationPage.passengers.selectByValue("4");
		reservationPage.selectDepartingFrom("New York", "October", "11");
		reservationPage.selectArrivingIn("San Francisco", "December", "26");
		reservationPage.businessClass.click();
		reservationPage.airline.selectByVisibleText("Unified Airlines");
		NewtoursReservation2Page reservation2Page = reservationPage.continueNext.click(NewtoursReservation2Page.class);
		reservation2Page.pangeaAirlines362.click();
		reservation2Page.unifiedAirlines633.click();
		NewtoursPurchasePage purchasePage = reservation2Page.continueNext.click(NewtoursPurchasePage.class);
		purchasePage.enterPassenger1Details("Sujay", "Sawant", "Low Calorie");
		purchasePage.enterPassenger2Details("Supriya", "Sawant", "Low Cholesterol");
		purchasePage.enterPassenger3Details("Vilas", "Sawant", "Diabetic");
		purchasePage.enterPassenger4Details("Neha", "Sawant", "Vegetarian");
		purchasePage.enterCreditCardDetails("MasterCard", "1234567891011121", "05", "2008", "Sujay", "Vilas", "Sawant");
		purchasePage.enterBillingAddress("Row House R-190", "Sector-4, Airoli", "Navi Mumbai", "Maharashtra", "400708", "UNITED KINGDOM");
		purchasePage.enterDeliveryAddress("Row House R-190", "Sector-4, Airoli", "Navi Mumbai", "Maharashtra", "400708", "UNITED STATES");
		NewtoursPurchase2Page purchase2Page = purchasePage.securePurchase.click(NewtoursPurchase2Page.class);
		purchase2Page.logOut.click();
	}

}
