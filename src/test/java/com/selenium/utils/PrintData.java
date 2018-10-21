package com.selenium.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintData {

	private static Logger logger = LoggerFactory.getLogger(PrintData.class);

	public static void endTestSuite(String testSuiteName) {
		logger.info("XXXXXXXXXXXXXXXXXXXXXXX             " + testSuiteName + "             XXXXXXXXXXXXXXXXXXXXXX");
		logger.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
	}

	public static String getRealtimewithTimeZone() {
		return "[" + new SimpleDateFormat("EEEEE, MMMM-dd-yyyy hh:mm:ss aa").format(Calendar.getInstance().getTime())
				+ " " + TimeZone.getDefault().getDisplayName(false, 0, Locale.ENGLISH) + "]";
	}

	public static void printEndingTestCase(String testCaseID) {
		logger.info("$$$$$$$$$$$$$$$$$$$$$$$$ ENDING TEST CASE - '" + testCaseID
				+ "' EXECUTION    $$$$$$$$$$$$$$$$$$$$$$$$");
	}

	public static void printErrorString(String logMessage) {
		logger.error(logMessage);
	}

	public static void printThrowable(Throwable throwable) {
		logger.error("", throwable);
	}

	public static void logError(String logMessage, Throwable throwable) {
		logger.error(logMessage, throwable);
	}

	public static void printStartingTestCase(String testCaseID) {
		PrintData.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ STARTING TEST CASE - '" + testCaseID
				+ "' EXECUTION  $$$$$$$$$$$$$$$$$$$$$$$$");
	}

	public static void printStartingTestCase(String testCaseID, String testDataIID) {
		PrintData.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ STARTING TEST CASE - '" + testCaseID
				+ "' EXECUTION with the testDataIID '" + testCaseID + "' $$$$$$$$$$$$$$$$$$$$$$$$");
	}

	public static void printStartingTestCaseGroup(String testCaseGroupIID) {
		PrintData.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ STARTING TEST CASE GROUP - '" + testCaseGroupIID
				+ "' EXECUTION  $$$$$$$$$$$$$$$$$$$$$$$$");
	}

	public static void logInfo(String logMessage) {
		System.out.println(logMessage);
	}

	public static void logInfoWithTabSpacedDescription(String description, String logMessage) {
		System.out.println(description + "\t\t:\t\t" + logMessage);
	}

	public static void logSpaceBtwMsg(int numberOfLines) {
		if (numberOfLines > 0) {
			for (int i = 0; i < numberOfLines; i++) {
				System.out.println();
			}
		}
	}

	public static boolean confirmMessage(String message, Object[] optionArray) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int response = JOptionPane.showOptionDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, optionArray, optionArray[0]);
		if (response == JOptionPane.YES_OPTION) {
			return true;
		} else if (response == JOptionPane.NO_OPTION) {
			return false;
		}
		return false;
	}

}