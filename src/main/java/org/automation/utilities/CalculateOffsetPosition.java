package org.automation.utilities;

import static org.automation.utilities.CursorPosition.CENTER;
import static org.automation.utilities.CursorPosition.TOP_LEFT;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;

/**
 * To calculate offsets which would be used to implement drag drop
 * functionality.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 08/31/2020
 *
 */
public final class CalculateOffsetPosition {

	private final WebElement parentElement;
	private final WebElement childElement;
	private final CursorPosition cursorPosition;
	private int xOffset = 0;
	private int yOffset = 0;

	/**
	 * Create object to calculate offset position.
	 * 
	 * @param parentElement
	 * @param childElement
	 * @param cursorPosition
	 */
	public CalculateOffsetPosition(WebElement parentElement, WebElement childElement, CursorPosition cursorPosition) {
		this.parentElement = parentElement;
		this.childElement = childElement;
		this.cursorPosition = cursorPosition;
		calculateOffset();
	}

	/**
	 * Get the X offset for the element.
	 * 
	 * @return X offset
	 */
	public int getXOffset() {
		return xOffset;
	}

	/**
	 * Get the Y offset for the element.
	 * 
	 * @return Y offset
	 */
	public int getYOffset() {
		return yOffset;
	}

	/**
	 * Calculate X and Y offset.
	 */
	private void calculateOffset() {
		int parentElementHeight = parentElement.getSize().getHeight();
		int parentElementWidth = parentElement.getSize().getWidth();
		int childElementHeight = childElement.getSize().getHeight();
		int childElementWidth = childElement.getSize().getWidth();

		if (childElementHeight >= parentElementHeight && childElementWidth >= parentElementWidth) {
			throw new ElementNotVisibleException("The child element is totally covering the parent element");
		}

		if (cursorPosition.equals(TOP_LEFT)) {
			xOffset = 1;
			yOffset = 1;
		}

		if (cursorPosition.equals(CENTER)) {
			if (childElementHeight < parentElementHeight) {
				xOffset = childElementHeight / 2 + 1;
			}
			if (childElementWidth < parentElementWidth) {
				yOffset = childElementWidth / 2 + 1;
			}
		}
	}

}
