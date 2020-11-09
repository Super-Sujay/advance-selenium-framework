package org.automation.elements;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.automation.logger.Log.info;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * This Class is used to handle <b>Table</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class Table extends Element {

	private List<String> headers, data;

	/**
	 * This constructor is used to create an object to access a <b>Table</b>.
	 * 
	 * @param description description of the table
	 * @param locator     locator of the table
	 */
	public Table(String description, By locator) {
		super(description, locator);
		headers = asList();
		data = asList();
	}

	/**
	 * Get all the table headers.
	 * 
	 * @return list of table headers
	 */
	public List<String> getTableHeaders() {
		if (headers.isEmpty()) {
			info("Get all the table headers from the [" + description + "] table");
			WebElement table = wait.until(elementToBeClickable(locator));
			headers = table.findElements(tagName("th")).stream().map(WebElement::getText).collect(toList());
		}
		return headers;
	}

	/**
	 * Get all the table data.
	 * 
	 * @return list of table data
	 */
	public List<String> getTableData() {
		if (data.isEmpty()) {
			info("Get all the table data from the [" + description + "] table");
			WebElement table = wait.until(elementToBeClickable(locator));
			data = table.findElements(tagName("td")).stream().map(WebElement::getText).collect(toList());
		}
		return data;
	}

	/**
	 * Get the table data from the specified row and header.
	 * 
	 * @param row    row in which data should be present
	 * @param header header in which data should be present
	 * @return table data
	 */
	public String getTableData(String row, String header) {
		info("Get the specific table data present in [" + row + "] row and [" + header + "] header from the ["
				+ description + "] table");
		if (headers.isEmpty()) {
			getTableHeaders();
		}
		if (data.isEmpty()) {
			getTableData();
		}
		if (headers.indexOf(header) != -1 && data.indexOf(row) != -1) {
			int dataIndex = data.indexOf(row) + headers.indexOf(header);
			return data.get(dataIndex);
		}
		return "Incorrect row or column header";
	}

	/**
	 * Get the table data from the specified row and header.
	 * 
	 * @param row    row number in which data should be present
	 * @param header header in which data should be present
	 * @return table data
	 */
	public String getTableData(int row, String header) {
		info("Get the specific table data present in [" + row + "] row number and [" + header + "] header from the ["
				+ description + "] table");
		if (headers.isEmpty()) {
			getTableHeaders();
		}
		WebElement table = wait.until(elementToBeClickable(locator));
		int column = headers.indexOf(header) + 1;
		try {
			return table
					.findElement(By.cssSelector("tbody > tr:nth-of-type(" + row + ") > td:nth-of-type(" + column + ")"))
					.getText();
		} catch (NoSuchElementException e) {
			return "Incorrect row number or column header";
		}
	}

	/**
	 * Get the table data from the specified row and column.
	 * 
	 * @param row    row number in which data should be present
	 * @param column column number in which data should be present
	 * @return table data
	 */
	public String getTableData(int row, int column) {
		info("Get the specific table data present in [" + row + "] row number and [" + column
				+ "] column number from the [" + description + "] table");
		WebElement table = wait.until(elementToBeClickable(locator));
		try {
			return table
					.findElement(By.cssSelector("tbody > tr:nth-of-type(" + row + ") > td:nth-of-type(" + column + ")"))
					.getText();
		} catch (NoSuchElementException e) {
			return "Incorrect row or column number";
		}
	}

}
