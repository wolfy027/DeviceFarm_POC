
package com.selenium.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public final class FDConstants {

	public static boolean ENABLE_ACTION_DEBUG_MODE = true;
	public static Boolean IS_REDIRECT_LOGS_ON_STARTUP_ON_CONSOLE = Boolean.TRUE;
	public static Integer APPIUM_SERVER_LISTENING_PORT = 4723;
	public final static String DEFAULT_TEXT_DESCRIPTION = "Enter Text Description Here";
	public final static String DEFAULT_IID_DESCRIPTION = "Enter IID Here";
	public final static Boolean DEFAULT_FALSE = Boolean.FALSE;
	public final static Boolean DEFAULT_TRUE = Boolean.TRUE;
	public final static Integer DEFAULT_INTEGER_ZERO = Integer.valueOf(0);
	public final static Integer DEFAULT_INTEGER_ONE = Integer.valueOf(1);
	public final static String COLON = ":";
	public final static char COMMENT_CHAR = '#';
	public final static String DELIMITER = "~";
	public final static String ENDEXP = "',";
	public final static int GOOGLE_CHROME = 1;
	public final static int INTERNET_EXPLORER = 2;
	public static boolean IS_PROD = true;
	// Default Time-out Settings
	public static int MAXIMUM_TIME_OUT_RANGE = 120;
	public final static int MOZILLA_FIREFOX = 3;
	public static int NO_OF_LINES_FOR_EXCEPTION = 3;
	public static String NOT_AVAIL = "N/A";
	public final static String OBJECT_SEPERATOR = ":";
	public final static String SINGLE_QUOTE_SEPERATOR = "'";
	public final static String SLASH = "|";
	public final static String STARTEXP = ":'";
	public final static String UNDERSCORE = "_";
	public final static String URL_DELIMETER = "/";
	public final static Charset UTF8 = Charset.forName("UTF-8");
	public final static String DOT = ".";
	public final static String HYPHEN = "-";
	public final static String TABLE_COL_TEXT_SEPARATOR = "¥";
	public final static String ERS_DATE_PATTERN = "MM/dd/yyyy";
	public final static String ERS_DATE_PATTERN2 = "MM-dd-yyyy";
	public final static String ERS_TIME_PATTERN = "HH:mm";
	public final static String ERS_WEEKDAY_PATTERN = "EEEE";
	public final static String HW_TIME_PATTERN = "hh:mm a";
	public final static String ERS_SQL_DATE_PATTERN = "dd-MMM-yyyy";
	public final static SimpleDateFormat ERS_DATE_FORMAT_OBJECT = new SimpleDateFormat(ERS_DATE_PATTERN);
	public final static SimpleDateFormat ERS_TIME_FORMAT_OBJECT = new SimpleDateFormat(ERS_TIME_PATTERN);
	public final static SimpleDateFormat HW_TIME_FORMAT_OBJECT = new SimpleDateFormat(HW_TIME_PATTERN);
	public final static String DATE_RANGE = "Select Date Range" + "1 Week" + "2 Week" + "4 Week" + "1 Month" + "3 Month"
			+ "6 Month" + "1 Year" + "2 Year" + "5 Year";
	public final static String WHITE_SPACE_REGEX = "\\s";
	public final static String ONE_BLANK_SPACE = " ";
	public final static String CLICK_BY_JS_SCRIPT = "arguments[0].click();";
	public final static String ERS_LEGACY_STORE_REGEX = "[0-9]+, .*";
	public final static String EMPTY_STRING = "";
	public final static String BACK_SLASH = "\\";
	public final static String FORWARD_SLASH = "/";
	public final static String NEW_LINE = "\n";
	public final static String HTML_TAG_REGEX = "<\\s*\\w.*?>";
	public final static String CELLTEXT_PLACEHOLDER_EXPRESSION = "\\{\\{(.*?)\\}\\}";
	public final static String HTML_TAG_REGEX2 = "<('[^']*'|'[^']*'|[^''>])*>";
	public final static List<String> MONTH_NAMES_LIST_IN_THREE_CHARS = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
	public final static boolean IS_DIFF_CHECKER_ENABLED = false;
	public static final String TIME_DATE_FORMAT = "HH:mm:ss    dd-MM-yyyy";
	public static boolean IS_EXECUTED_LOCALLY = true;

	private FDConstants() {
		;
	}
}