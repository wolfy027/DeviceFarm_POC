package tests.calculator.dataProviders;

import org.testng.annotations.DataProvider;

public class CalculatorDataProvider {

	@DataProvider(name = "operands")
	public static Object[][] operands() {
		return new Object[][] { { "67", "67" },{ "123", "980" } ,{ "56", "1001" } ,{ "098", "789" }  };
	}

}
