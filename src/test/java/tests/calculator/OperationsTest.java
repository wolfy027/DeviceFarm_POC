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

public class OperationsTest {
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

	@Test(groups = {
			"operations" }, testName = "addNumbers", dataProvider = "operands", dataProviderClass = CalculatorDataProvider.class, priority = 1, enabled = true)
	public void addOperands(String operand1, String operand2) {
		AndroidDriverUtil.inputNumbers(operand1, driver);
		driver.findElement(By.id("com.softdx.calculator:id/plus")).click();
		AndroidDriverUtil.inputNumbers(operand2, driver);
		driver.findElement(By.id("com.softdx.calculator:id/equal")).click();
		int expected = Integer.parseInt(operand1) + Integer.parseInt(operand2);
		int actual = Integer.parseInt(AndroidDriverUtil.getResult(driver));
		AndroidDriverUtil.takeScreenshot("ADD_OPERANDS", driver);
		driver.findElement(By.id("com.softdx.calculator:id/allClear")).click();
		Assert.assertEquals(expected, actual);
	}

	@Test(groups = {
			"operations" }, testName = "subtractNumbers", dataProvider = "operands", dataProviderClass = CalculatorDataProvider.class, priority = 1, enabled = true)
	public void subtractOperands(String operand1, String operand2) {
		AndroidDriverUtil.inputNumbers(operand1, driver);
		driver.findElement(By.id("com.softdx.calculator:id/minus")).click();
		AndroidDriverUtil.inputNumbers(operand2, driver);
		driver.findElement(By.id("com.softdx.calculator:id/equal")).click();
		int expected = Integer.parseInt(operand1) - Integer.parseInt(operand2);
		int actual = Integer.parseInt(AndroidDriverUtil.getResult(driver));
		AndroidDriverUtil.takeScreenshot("SUBTRACT_OPERANDS", driver);
		driver.findElement(By.id("com.softdx.calculator:id/allClear")).click();
		Assert.assertEquals(expected, actual);
	}

	@Test(groups = {
			"operations" }, testName = "multiplyNumbers", dataProvider = "operands", dataProviderClass = CalculatorDataProvider.class, priority = 1, enabled = true)
	public void multiplyOperands(String operand1, String operand2) {
		AndroidDriverUtil.inputNumbers(operand1, driver);
		driver.findElement(By.id("com.softdx.calculator:id/mul")).click();
		AndroidDriverUtil.inputNumbers(operand2, driver);
		driver.findElement(By.id("com.softdx.calculator:id/equal")).click();
		int expected = Integer.parseInt(operand1) * Integer.parseInt(operand2);
		int actual = Integer.parseInt(AndroidDriverUtil.getResult(driver));		
		AndroidDriverUtil.takeScreenshot("MULTIPLY_OPERANDS", driver);
		driver.findElement(By.id("com.softdx.calculator:id/allClear")).click();
		Assert.assertEquals(expected, actual);
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
