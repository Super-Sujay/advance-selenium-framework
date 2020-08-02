package org.automation.pages;

import static org.openqa.selenium.By.cssSelector;

import org.automation.base.BasePage;
import org.automation.elements.Button;

public class NewtoursPurchase2Page extends BasePage {

	public Button backToFlights = new Button("Back To Flights", cssSelector("a[href='mercuryreservation.php'] > img"));
	public Button backToHome = new Button("Back To Home", cssSelector("a[href='mercurywelcome.php'] > img"));
	public Button logOut = new Button("Log Out", cssSelector("a[href='mercurysignoff.php'] > img"));

}
