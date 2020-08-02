package org.automation.pages;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.name;

import org.automation.base.BasePage;
import org.automation.elements.Button;
import org.automation.elements.DropDown;
import org.automation.elements.RadioButton;

public class NewtoursReservationPage extends BasePage {

	public RadioButton roundTrip = new RadioButton("Round Trip", cssSelector("input[value=roundtrip]"));
	public RadioButton oneWay = new RadioButton("One Way", cssSelector("input[value=oneway]"));
	public DropDown passengers = new DropDown("Passengers", name("passCount"));
	public DropDown departingFrom = new DropDown("Departing From", name("fromPort"));
	public DropDown fromMonth = new DropDown("From Month", name("fromMonth"));
	public DropDown fromDay = new DropDown("From Day", name("fromDay"));
	public DropDown arrivingIn = new DropDown("Arriving In", name("toPort"));
	public DropDown toMonth = new DropDown("To Month", name("toMonth"));
	public DropDown toDay = new DropDown("To Day", name("toDay"));
	public RadioButton economyClass = new RadioButton("Economy Class", cssSelector("input[value=Coach]"));
	public RadioButton businessClass = new RadioButton("Business Class", cssSelector("input[value=Business]"));
	public RadioButton firstClass = new RadioButton("First Class", cssSelector("input[value=First]"));
	public DropDown airline = new DropDown("Airline", name("airline"));
	public Button continueNext = new Button("Continue", name("findFlights"));

	public void selectDepartingFrom(String airport, String month, String date) {
		departingFrom.selectByValue(airport);
		fromMonth.selectByVisibleText(month);
		fromDay.selectByValue(date);
	}

	public void selectArrivingIn(String airport, String month, String date) {
		arrivingIn.selectByValue(airport);
		toMonth.selectByVisibleText(month);
		toDay.selectByValue(date);
	}

}
