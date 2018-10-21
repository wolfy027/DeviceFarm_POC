package tests.calculator;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.selenium.utils.FDConstants;

import Util.AndroidDriverUtil;
import Util.MobileDriverUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import tests.calculator.dataProviders.CalculatorDataProvider;

public class ValidationTest {
	public static AndroidDriver<AndroidElement> driver = null;

	@BeforeSuite
	private void setAppium() {
		if (FDConstants.IS_EXECUTED_LOCALLY) {
			driver = MobileDriverUtil.getAndroidDriver("One_Plus3T", "emulator-5554", "7.1.1", "Android",
					"uiautomator2", "/app/samsung_calculator.apk", "com.softdx.calculator.Calculator",
					"http://127.0.0.1:4723/wd/hub");
		} else {
			driver = MobileDriverUtil.getAndroidDriverForDeviceFarm();
		}
		driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
		System.out
				.println("Android Version\t:\t" + driver.getCapabilities().getCapability("platformVersion").toString());
		if (!driver.getCapabilities().getCapability("platformVersion").toString().startsWith("4")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
			driver.getCapabilities().merge(capabilities);
		} else {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
			driver.getCapabilities().merge(capabilities);
		}
		initPageObjects();
	}

	@Test(groups = { "validation" }, testName = "validateElements", priority = 1, enabled = true)
	public void validateElements() {
		AndroidDriverUtil.takeScreenshot("VALIDATE_EQUALS", driver);
		Assert.assertTrue(driver.findElements(By.id("com.softdx.calculator:id/equal")).size() > 0);
	}

	@AfterMethod
	public void resetScreenshotCounter() {
		AndroidDriverUtil.resetScreenshotCounter();
	}

	private void initPageObjects() {
	}

	@AfterSuite
	public void tearDown() throws Exception {
		driver.quit();
	}

}
