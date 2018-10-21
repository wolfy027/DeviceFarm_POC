package samsung.calculator.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.selenium.utils.PrintData;

import Util.LoggerUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * The Class GenericPage.
 */
public class GenericPage {

	/**
	 * Instantiates a new generic page.
	 */
	public GenericPage() {
		super();
	}

	/** The instance. */
	private static GenericPage instance;

	/** The mob driver. */
	protected AndroidDriver<AndroidElement> mobDriver;

	/**
	 * Instantiates a new generic page.
	 *
	 * @param mobDriver the mob driver
	 */
	public GenericPage(AndroidDriver<AndroidElement> mobDriver) {
		this.mobDriver = mobDriver;
	}

	/**
	 * Clear text.
	 *
	 * @param element the element
	 */
	public void clearText(WebElement element) {
		if (element.getText().length() > 0) {
			element.clear();
		}
	}

	/**
	 * Gets the single instance of GenericPage.
	 *
	 * @param mobDriver the mob driver
	 * @return single instance of GenericPage
	 */
	public static GenericPage getInstance(AndroidDriver<AndroidElement> mobDriver) {
		return instance == null ? new GenericPage(mobDriver) : instance;
	}

/*	*//**
	 * Handle loading layer.
	 *//*
	public void handleLoadingLayer() {
		mobDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
			if (mobDriver.findElements(progressBar_Locator).size() > 0) {
				new WebDriverWait(mobDriver, 120).until(ExpectedConditions
						.invisibilityOfElementLocated(By.id("com.altametrics.zipschedulesers:id/progressBar1")));
			}
		} catch (Exception e) {
			LoggerUtils.printInfo("Loader does not exist.");
		}
		if (mobDriver.findElements(progressBar_Locator).size() > 0)
			handleLoadingLayer();
	}*/

	/**
	 * Gets the mob driver.
	 *
	 * @return the mob driver
	 */
	public AndroidDriver<AndroidElement> getMobDriver() {
		return mobDriver;
	}

	/**
	 * Sets the mob driver.
	 *
	 * @param mobDriver the new mob driver
	 */
	public void setMobDriver(AndroidDriver<AndroidElement> mobDriver) {
		this.mobDriver = mobDriver;
	}

	/**
	 * Wait for seconds.
	 *
	 * @param value the value
	 */
	public void waitForSeconds(int value) {
		try {
			Thread.sleep(value * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Click action using by.
	 *
	 * @param byElement the by element
	 */
	public void clickActionUsingBy(By byElement) {
		if (mobDriver.findElement(byElement).isDisplayed()) {
			mobDriver.findElement(byElement).click();
		} else {
			LoggerUtils.printInfo("Element does not exist.");
		}
	}

	/**
	 * Check alert.
	 *
	 * @param locator          the locator
	 * @param messageFromSetup the message from setup
	 * @param dynamicMessage   the dynamic message
	 * @return true, if successful
	 */
	public boolean checkAlert(By locator, String messageFromSetup, String dynamicMessage) {
		if (messageFromSetup.length() == 0) {
			LoggerUtils.printInfo("");
			PrintData.logInfo(
					"Blank message detected.Please enter a alert message text in the message hash to be validated against.");
			return false;
		}
		if (dynamicMessage != null) {
			messageFromSetup = messageFromSetup.replace("$DYNAMIC_MESSAGE$", dynamicMessage);
		}
		String alertMessageText = "";
		try {
			MobileElement alertMessageElement = mobDriver.findElement(locator);
			alertMessageText = alertMessageElement.getText();
			PrintData.logInfoWithTabSpacedDescription("Alert Message Text", alertMessageText);
			PrintData.logInfoWithTabSpacedDescription("Message from Setup", messageFromSetup);
		} catch (Exception e) {
			LoggerUtils.printInfo("Alert Box was not detected.");
		}
		return (alertMessageText.equals(messageFromSetup));

	}

/*	*//**
	 * Gets the dynamic xpath.
	 *
	 * @param xpath the xpath
	 * @param data  the data
	 * @return the dynamic xpath
	 *//*
	public String getDynamicXpath(String xpath, String data) {
		String elementXpath = this.getValueFromXpath(xpath);
		if (elementXpath.contains("DYN1")) {
			elementXpath = elementXpath.replace("DYN1", data);
		}
		return elementXpath;
	}*/

	/**
	 * Hide soft keyboard.
	 */
	public void hideSoftKeyboard() {
		try {
			mobDriver.hideKeyboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send text after clear.
	 *
	 * @param clearText the clear text
	 * @param element   the element
	 * @param testData  the test data
	 */
	public void sendTextAfterClear(boolean clearText, WebElement element, String testData) {
		if (clearText) {
			element.clear();
			element.sendKeys(testData);
		}
	}

	public String getElementText(String element) {
		return mobDriver.findElementById(element).getText();
	}

	/**
	 * Verify input with UI.
	 *
	 * @param _val1  the val 1
	 * @param _val2  the val 2
	 * @param action the action
	 * @return true, if successful
	 */
	public boolean verifyInputWithUI(String _val1, String _val2, String action) {
		if (action.equalsIgnoreCase("contains")) {
			return _val1.contains(_val2);
		} else {
			return _val1.equals(_val2);
		}
	}

}