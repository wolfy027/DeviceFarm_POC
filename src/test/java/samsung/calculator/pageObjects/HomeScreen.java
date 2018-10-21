package samsung.calculator.pageObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class HomeScreen extends GenericPage {
	private static HomeScreen instance;

	public HomeScreen(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}




	public static HomeScreen getInstance(AndroidDriver<AndroidElement> driver) {
		if (instance == null) {
			instance = new HomeScreen(driver);
		}
		return instance;
	}
}
