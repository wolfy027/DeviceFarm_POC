package Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.selenium.utils.FDConstants;

import samsung.calculator.pageObjects.GenericPage;

/**
 * The Class ConsolLog.
 */
public class LoggerUtils extends GenericPage {

	/**
	 * Prints the info.
	 *
	 * @param _val
	 *            the val
	 */
	public static void printInfo(String _val) {
		System.out.println(
				getCurrentTime() + " | " + getCurrentTimeZone() + " | " + getCurrentClassName() + " |INFO |" + _val);
	}

	/**
	 * Prints the info with description.
	 *
	 * @param _val1
	 *            the val 1
	 * @param _val2
	 *            the val 2
	 */
	public static void printInfoWithDescription(String _val1, String _val2) {
		System.out.println(getCurrentTime() + " | " + getCurrentTimeZone() + " | " + getCurrentClassName() + " |INFO |"
				+ _val1 + "  : \t" + _val2);
	}

	/**
	 * Prints the exception.
	 *
	 * @param throwable
	 *            the throwable
	 */
	public static void printException(Throwable throwable) {
		System.out.println(getCurrentTime() + " | " + getCurrentTimeZone() + " | " + getCurrentClassName() + " |INFO |"
				+ throwable);
	}

	/**
	 * Prints the exception with description.
	 *
	 * @param _val1
	 *            the val 1
	 * @param _val2
	 *            the val 2
	 */
	public static void printExceptionWithDescription(String _val1, Throwable _val2) {
		System.out.println(getCurrentTime() + " | " + getCurrentTimeZone() + " | " + getCurrentClassName() + " |INFO |"
				+ _val1 + "  : \t" + _val2);
	}

	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	private static String getCurrentTime() {
		return DateTimeFormatter.ofPattern(FDConstants.TIME_DATE_FORMAT).format(LocalDateTime.now());
	}

	/**
	 * Gets the current time zone.
	 *
	 * @return the current time zone
	 */
	private static String getCurrentTimeZone() {
		return Calendar.getInstance().getTimeZone().getDisplayName();
	}

	/**
	 * Gets the current class name.
	 *
	 * @return the current class name
	 */
	private static String getCurrentClassName() {
		return LoggerUtils.class.getName();
	}

}
