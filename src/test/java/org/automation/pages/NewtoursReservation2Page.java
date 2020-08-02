package org.automation.pages;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.name;

import org.automation.base.BasePage;
import org.automation.elements.Button;
import org.automation.elements.RadioButton;

public class NewtoursReservation2Page extends BasePage {

	public RadioButton blueSkiesAirlines360 = new RadioButton("Blue Skies Airlines 360", cssSelector("input[value^='Blue Skies Airlines$360']"));
	public RadioButton blueSkiesAirlines361 = new RadioButton("Blue Skies Airlines 361", cssSelector("input[value^='Blue Skies Airlines$361']"));
	public RadioButton pangeaAirlines362 = new RadioButton("Pangea Airlines 362", cssSelector("input[value^='Pangea Airlines$362']"));
	public RadioButton unifiedAirlines363 = new RadioButton("Unified Airlines 363", cssSelector("input[value^='Unified Airlines$363']"));
	public RadioButton blueSkiesAirlines630 = new RadioButton("Blue Skies Airlines 630", cssSelector("input[value^='Blue Skies Airlines$630']"));
	public RadioButton blueSkiesAirlines631 = new RadioButton("Blue Skies Airlines 360", cssSelector("input[value^='Blue Skies Airlines$631']"));
	public RadioButton pangeaAirlines632 = new RadioButton("Pangea Airlines 632", cssSelector("input[value^='Pangea Airlines$632']"));
	public RadioButton unifiedAirlines633 = new RadioButton("Unified Airlines 633", cssSelector("input[value^='Unified Airlines$633']"));
	public Button continueNext = new Button("Continue", name("reserveFlights"));

}
