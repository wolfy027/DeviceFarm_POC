package Util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class MobileDriverUtil {

	public static AndroidDriver<AndroidElement> getAndroidDriverForDeviceFarm() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1000);
		try {
			return new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static AndroidDriver<AndroidElement> getAndroidDriver(String deviceName, String udid, String androidVersion,
			String platformName, String automationName, String apkPath, String appActivity, String appiumUrl) {
		AndroidDriver<AndroidElement> driver = null;
		DesiredCapabilities capabilities = DesiredCapabilities.android();
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("udid", udid);
		capabilities.setCapability(CapabilityType.VERSION, androidVersion);
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("automationName", automationName);
		capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + apkPath);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
		capabilities.setCapability("newCommandTimeout", 10000);
		try {
			driver = new AndroidDriver<AndroidElement>(new URL(appiumUrl), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return driver;
	}

	public static String handlePlaceHolderVariables(String cellText) {
		String randomTestData = "";
		if (cellText.toLowerCase().startsWith("random_")) {
			String[] randParams = cellText.split("_");
			int count = Integer.parseInt(randParams[2]);
			if (cellText.toLowerCase().contains("string")) {
				randomTestData = RandomStringUtils.randomAlphabetic(count);
			} else if (cellText.toLowerCase().contains("number")) {
				randomTestData = "9".concat(RandomStringUtils.randomNumeric(count - 1));
			} else if (cellText.toLowerCase().contains("specialchar")) {
				randomTestData = RandomStringUtils.randomAscii(count);
			} else if (cellText.toLowerCase().contains("email")) {
				randomTestData = RandomStringUtils.randomAlphanumeric(count).concat("@")
						.concat(RandomStringUtils.randomAlphabetic(count)).concat(".")
						.concat(RandomStringUtils.randomAlphabetic(3));
			} else {
				randomTestData = RandomStringUtils.randomAlphanumeric(count);
			}
		}
		return randomTestData;
	}
}
