package org.automation.pages;

import static org.openqa.selenium.By.name;

import org.automation.base.BasePage;
import org.automation.elements.Button;
import org.automation.elements.TextBox;

public class NewtoursSignOnPage extends BasePage {

	public TextBox username = new TextBox("User Name", name("userName"));
	public TextBox password = new TextBox("Password", name("password"));
	public Button submit = new Button("Submit", name("login"));

	public NewtoursReservationPage signIn(String username, String password) {
		this.username.enterText(username);
		this.password.enterText(password);
		return submit.click(NewtoursReservationPage.class);
	}

}
