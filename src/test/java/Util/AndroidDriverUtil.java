package Util;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;

public class AndroidDriverUtil {
	public static int SCREENSHOT_COUNTER = 0;

	public static boolean takeScreenshot(final String name, WebDriver driver) {
		String invokingMethodName = new Exception().getStackTrace()[1].getMethodName();
		String screenshotFileName = invokingMethodName + "_" + (++SCREENSHOT_COUNTER) + "_" + name;
		String screenshotDirectory = System.getProperty("appium.screenshots.dir",
				System.getProperty("java.io.tmpdir", ""));
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", screenshotFileName)));
	}

	public static void resetScreenshotCounter() {
		SCREENSHOT_COUNTER = 0;
	}

	public static DesiredCapabilities setAndroidUnicodeKeyboard(DesiredCapabilities capabilities) {
		capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
		capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		return capabilities;
	}

	public static void inputNumbers(String operand, AndroidDriver<AndroidElement> driver) {
		for (char c : operand.toCharArray()) {
			driver.findElement(By.id("com.softdx.calculator:id/digit" + c)).click();
		}
	}

	public static String getResult(AndroidDriver<AndroidElement> driver) {
		return driver.findElement(By.id("com.softdx.calculator:id/display_Res_Text")).getText().replace(",", "")
				.replace("minus", "-").split("equals")[1].trim();
	}

}
