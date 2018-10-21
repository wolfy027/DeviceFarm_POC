package samsung.calculator.pageObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LoginScreen extends GenericPage {
	protected static LoginScreen instance;

	public LoginScreen(AndroidDriver<AndroidElement> mobDriver) {
		super(mobDriver);
	}

	public static LoginScreen getInstance(AndroidDriver<AndroidElement> mobDriver) {
		if (instance == null) {
			instance = new LoginScreen(mobDriver);
			return instance;
		}

		return instance;
	}

	
}
