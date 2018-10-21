package com.selenium.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class DateUtils {



	public static String getDateBeforeInputDate(String durationParameter, String formate, String date) {
		SimpleDateFormat format = new SimpleDateFormat(formate);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date));
		if (durationParameter.toLowerCase().contains("month")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.MONTH, -digit);
		} else if (durationParameter.toLowerCase().contains("week")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.WEEK_OF_YEAR, -digit);
		} else if (durationParameter.toLowerCase().contains("year")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.YEAR, -digit);
		} else if (durationParameter.toLowerCase().contains("day")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.DAY_OF_YEAR, -digit);
		}
		Date date1 = c.getTime();
		return format.format(date1);
	}

	public static String getDateAfterInputDate(String durationParameter, String dateFormat, String date) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);//
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date));
		if (durationParameter.toLowerCase().contains("month")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.MONTH, +digit);
		} else if (durationParameter.toLowerCase().contains("week")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.WEEK_OF_YEAR, +digit);
		} else if (durationParameter.toLowerCase().contains("year")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.YEAR, +digit);
		} else if (durationParameter.toLowerCase().contains("day")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.DAY_OF_YEAR, +digit);
		}
		Date date1 = c.getTime();
		return format.format(date1);
	}

	public static List<String> adjustmentPeriodValues(String date, String formate, String durationParameter,
			int startDayIndex) {
		SimpleDateFormat format = new SimpleDateFormat(formate);
		Calendar c = Calendar.getInstance();
		String[] days = null;
		PrintData.logInfo("date :\t\t" + date);
		c.setTime(new Date(date));
		if (durationParameter.toLowerCase().contains("month")) {
			days = new String[c.getActualMaximum(Calendar.DAY_OF_MONTH)];
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			for (int i = 0; i < days.length; i++) {
				days[i] = format.format(c.getTime());
				c.add(Calendar.DAY_OF_MONTH, 1);

			}
		} else if (durationParameter.toLowerCase().contains("week")) {
			days = (String[]) getWeekUsingStartDay(c.getTime(), startDayIndex).toArray();

		} else if (durationParameter.toLowerCase().contains("day")) {
			days = new String[] { "12A", "1A", "2A", "3A", "4A", "5A", "6A", "7A", "8A", "9A", "10A", "11A", "12P",
					"1P", "2P", "3P", "4P", "5P", "6P", "7P", "8P", "9P", "10P", "11P" };

		}
		return Arrays.asList(days);
	}

	public static List<String> getWeekUsingStartDay(Date date, int day) {
		List<String> week = new ArrayList<String>(7);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, day);
		String[] days = new String[7];
		for (int i = 0; i < 7; i++) {
			days[i] = format.format(calendar.getTime());
			calendar.add(Calendar.DATE, 1);

		}
		week = Arrays.asList(days);
		return week;

	}



	public static Date getParsedDate(String strDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getSqlDateString(String date, String delimiter) {
		String[] dateParts = date.split(delimiter);
		String sqlDate = "";
		int monthPart = Integer.valueOf(dateParts[0]);
		switch (monthPart) {
		case 1:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "JAN" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 2:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "FEB" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 3:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "MAR" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 4:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "APR" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 5:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "MAY" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 6:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "JUN" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 7:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "JUL" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 8:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "AUG" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 9:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "SEP" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 10:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "OCT" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 11:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "NOV" + FDConstants.HYPHEN + dateParts[2];
			break;
		case 12:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "DEC" + FDConstants.HYPHEN + dateParts[2];
			break;
		default:
			sqlDate = dateParts[1] + FDConstants.HYPHEN + "JAN" + FDConstants.HYPHEN + dateParts[2];
			break;
		}

		return sqlDate;
	}

	public static String getErsFormatDate(String testDataText) {
		String result = "";
		DateTime dt = new DateTime();
		try {
			if (testDataText.toLowerCase().startsWith("current_date")) {

				if (testDataText.contains("+")) {
					Date addedDate = dt.plusDays(Integer.valueOf(testDataText.split("\\+")[1])).toDate();
					result = FDConstants.ERS_DATE_FORMAT_OBJECT.format(addedDate);
				}
				if (testDataText.contains("-")) {
					Date subtractedDate = dt.minusDays(Integer.valueOf(testDataText.split("\\-")[1])).toDate();
					result = FDConstants.ERS_DATE_FORMAT_OBJECT.format(subtractedDate);
				}

				if (result.equals("")) {
					result = FDConstants.ERS_DATE_FORMAT_OBJECT.format(new Date());
				}
			}
		} catch (Exception e) {
			return testDataText;
		}
		return result;
	}

	public static String getErsFormatDateBasedOnTimeZone(String testDataText) {
		String result = "";
		DateTime dateTimeUtc = new DateTime(new java.util.Date(), DateTimeZone.UTC);
		DateTimeZone timeZone = DateTimeZone.forID(testDataText.split("_")[0]);
		LocalDateTime dt = dateTimeUtc.withZone(timeZone).toLocalDateTime();
		try {
			if (testDataText.toLowerCase().contains("_time_zone_date")) {
				if (testDataText.contains("+")) {
					Date addedDate = dt.plusDays(Integer.valueOf(testDataText.split("\\+")[1])).toDate();
					result = FDConstants.ERS_DATE_FORMAT_OBJECT.format(addedDate);
				}
				if (testDataText.contains("-")) {
					Date subtractedDate = dt.minusDays(Integer.valueOf(testDataText.split("\\-")[1])).toDate();
					result = FDConstants.ERS_DATE_FORMAT_OBJECT.format(subtractedDate);
				}

				if (result.equals("")) {
					result = FDConstants.ERS_DATE_FORMAT_OBJECT.format(dt.toDate());
				}
			}
		} catch (Exception e) {
			return testDataText;
		}
		return result;
	}

	public static String getErsFormatTimeBasedOnTimeZone(String testDataText) {
		String result = "";
		DateTime dateTimeUtc = new DateTime(new java.util.Date(), DateTimeZone.UTC);
		DateTimeZone timeZone = DateTimeZone.forID(testDataText.split("_")[0]);
		LocalDateTime dt = dateTimeUtc.withZone(timeZone).toLocalDateTime();
		String[] tokens = null;
		if (testDataText.contains("+")) {
			tokens = testDataText.split("\\+")[1].split(":");
		} else if (testDataText.contains("-")) {
			tokens = testDataText.split("\\-")[1].split(":");
		} else {
			return FDConstants.ERS_TIME_FORMAT_OBJECT.format(dt.toDate());
		}
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		int durationInMinutes = (60 * hours) + minutes;
		try {
			if (testDataText.toLowerCase().contains("_time_zone_time")) {
				if (testDataText.contains("+")) {
					Date addedDate = dt.plusMinutes(durationInMinutes).toDate();
					result = FDConstants.ERS_TIME_FORMAT_OBJECT.format(addedDate);
				}
				if (testDataText.contains("-")) {
					Date subtractedDate = dt.minusMinutes(durationInMinutes).toDate();
					result = FDConstants.ERS_TIME_FORMAT_OBJECT.format(subtractedDate);
				}
				if (result.equals("")) {
					result = FDConstants.ERS_TIME_FORMAT_OBJECT.format(dt.toDate());
				}
			}
		} catch (Exception e) {
			return testDataText;
		}
		return result;
	}

	public static String formatDateToString(Date date, String format, String timeZone) {
		if (date == null)
			return null;
		// create SimpleDateFormat object with input format
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// default system timezone if passed null or empty
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		// set timezone to SimpleDateFormat
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		// return Date in required format with timezone as String
		return sdf.format(date);
	}

	public static String[] getSixWeekBefore(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.WEDNESDAY);
		calendar.add(Calendar.DAY_OF_MONTH, -42);
		String[] days = new String[6];
		for (int i = 0; i < 5; i++) {
			days[i] = format.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 7);
		}
		days[5] = "Last Year";
		return days;

	}

	public static List<String> getWeek(Date date) {
		List<String> week = new ArrayList<String>(7);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.WEDNESDAY);
		String[] days = new String[7];
		for (int i = 0; i < 7; i++) {
			days[i] = format.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);

		}
		week = Arrays.asList(days);
		return week;

	}

	public static String getMMDDYYYYDate(String dateInString) {
		Calendar cal = null;
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
		try {

			Date date1 = formatter.parse(dateInString);
			cal = Calendar.getInstance();
			cal.setTime(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatter1.format(cal.getTime());
	}

	public static String getBeforeDate(String durationParameter, boolean isForSql) {
		SimpleDateFormat format = new SimpleDateFormat(
				(isForSql ? FDConstants.ERS_SQL_DATE_PATTERN : FDConstants.ERS_DATE_PATTERN));
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (durationParameter.toLowerCase().contains("month")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.MONTH, -digit);
		} else if (durationParameter.toLowerCase().contains("week")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.WEEK_OF_YEAR, -digit);
		} else if (durationParameter.toLowerCase().contains("year")) {
			int digit = MathUtils.takeDigits(durationParameter);
			c.add(Calendar.YEAR, -digit);
		}
		Date date = c.getTime();
		return format.format(date);
	}

	public static String todaySqlDate() {
		DateFormat dateFormat = new SimpleDateFormat(FDConstants.ERS_SQL_DATE_PATTERN);
		return dateFormat.format(new Date()).trim();
	}

	public static String todayDate() {
		DateFormat dateFormat = FDConstants.ERS_DATE_FORMAT_OBJECT;
		return dateFormat.format(new Date()).trim();
	}

	public static int getDiffYears(Date first, Date last) {
		Calendar a = getCalendar(first);
		Calendar b = getCalendar(last);
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
				|| (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
			diff--;
		}
		return diff;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}

	/**
	 * 
	 * This method derives all the dates in between two dates passed as
	 * parameter.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 *             <br/>
	 *             created on : March 1, 2016
	 */
	public static List<String> getDatesInRange(String date1, String date2) throws ParseException {
		List<String> dateList = new ArrayList<>();
		final DateFormat formatter = FDConstants.ERS_DATE_FORMAT_OBJECT;
		Date startDate = formatter.parse(date1);
		Date endDate = formatter.parse(date2);
		long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
		long endTime = endDate.getTime(); // create your endtime here, possibly
		// using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
			dateList.add(formatter.format(new Date(curTime)));
			curTime += interval;
		}
		return dateList;
	}

	public static boolean compareDateWithSystemDate(String date) {
		DateFormat dateFormat = FDConstants.ERS_DATE_FORMAT_OBJECT;
		String str = dateFormat.format(new Date());
		return date.trim().equals(str.trim());
	}

	public static boolean isDateBetweenTwoDates(String date1, String date2, List<String> colValues) {
		DateFormat formatter;
		formatter = FDConstants.ERS_DATE_FORMAT_OBJECT;
		boolean flag = false;
		if (colValues.isEmpty()) {
			PrintData.logInfo("Missing prerequisite : Data Not Found ! ");
			return true;
		}
		PrintData.logInfo("List of Date is " + colValues.toString());

		for (int k = 0; k < colValues.size(); k++) {
			Date colDate = null;
			try {
				colDate = formatter.parse(colValues.get(k));

				if (colDate.after(formatter.parse(date1))
						|| (colDate.compareTo(formatter.parse(date1)) == 0) && colDate.before(formatter.parse(date2))
						|| (colDate.compareTo(formatter.parse(date2)) == 0)) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			} catch (ParseException e) {
			}
		}
		return flag;
	}

	public static String getSqlDateStringWithSpace(String date) {
		PrintData.logInfo("Date\t\t:" + date);
		String[] dateParts = date.split("/");
		String sqlDate = "";
		switch (dateParts[0]) {
		case "01":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "JAN" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "02":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "FEB" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "03":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "MAR" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "04":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "APR" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "05":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "MAY" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "06":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "JUN" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "07":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "JUL" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "08":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "AUG" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "09":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "SEP" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "10":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "OCT" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "11":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "NOV" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		case "12":
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "DEC" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		default:
			sqlDate = dateParts[1] + FDConstants.ONE_BLANK_SPACE + "JAN" + FDConstants.ONE_BLANK_SPACE + dateParts[2];
			break;
		}
		return sqlDate;
	}

	public static String getStartDate(String rangeParam) {
		SimpleDateFormat format = FDConstants.ERS_DATE_FORMAT_OBJECT;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (rangeParam.toLowerCase().contains("m")) {
			int digit = MathUtils.takeDigits(rangeParam);
			c.add(Calendar.MONTH, -digit);
		} else if (rangeParam.toLowerCase().contains("w")) {
			int digit = MathUtils.takeDigits(rangeParam);
			c.add(Calendar.WEEK_OF_YEAR, -digit);
		} else if (rangeParam.toLowerCase().contains("y")) {
			int digit = MathUtils.takeDigits(rangeParam);
			c.add(Calendar.YEAR, -digit);
		}
		Date date = c.getTime();
		String startDate = format.format(date);
		return startDate;
	}

	public static String getMonthNameWithIndex(String monthIndex) {
		String sqlDate = "";
		switch (monthIndex) {
		case "01":
			sqlDate = "JAN";
			break;
		case "02":
			sqlDate = "FEB";
			break;
		case "03":
			sqlDate = "MAR";
			break;
		case "04":
			sqlDate = "APR";
			break;
		case "05":
			sqlDate = "MAY";
			break;
		case "06":
			sqlDate = "JUN";
			break;
		case "07":
			sqlDate = "JUL";
			break;
		case "08":
			sqlDate = "AUG";
			break;
		case "09":
			sqlDate = "SEP";
			break;
		case "10":
			sqlDate = "OCT";
			break;
		case "11":
			sqlDate = "NOV";
			break;
		case "12":
			sqlDate = "DEC";
			break;
		default:
			sqlDate = "JAN";
			break;
		}
		return sqlDate;
	}

	public static String getMonthFirstCharInCapsNameWithIndex(String monthIndex) {
		String monthNameInThreeCharacters = "";
		switch (monthIndex) {
		case "01":
			monthNameInThreeCharacters = "Jan";
			break;
		case "02":
			monthNameInThreeCharacters = "Feb";
			break;
		case "03":
			monthNameInThreeCharacters = "Mar";
			break;
		case "04":
			monthNameInThreeCharacters = "Apr";
			break;
		case "05":
			monthNameInThreeCharacters = "May";
			break;
		case "06":
			monthNameInThreeCharacters = "Jun";
			break;
		case "07":
			monthNameInThreeCharacters = "Jul";
			break;
		case "08":
			monthNameInThreeCharacters = "Aug";
			break;
		case "09":
			monthNameInThreeCharacters = "Sep";
			break;
		case "10":
			monthNameInThreeCharacters = "Oct";
			break;
		case "11":
			monthNameInThreeCharacters = "Nov";
			break;
		case "12":
			monthNameInThreeCharacters = "Dec";
			break;
		default:
			monthNameInThreeCharacters = "Jan";
			break;
		}
		return monthNameInThreeCharacters;
	}

	public static String getFutureWeekOffsetDate(int appDayOffset, int adjustment) {
		Calendar calendarInstance = Calendar.getInstance();
		calendarInstance.add(Calendar.DATE, 7);
		calendarInstance.set(Calendar.DAY_OF_WEEK, appDayOffset);
		calendarInstance.add(Calendar.DATE, adjustment);
		return FDConstants.ERS_DATE_FORMAT_OBJECT.format(calendarInstance.getTime());
	}

	public static String getAddTimeInSystemTime(String time) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		df.setTimeZone(TimeZone.getTimeZone(""));
		Calendar.getInstance().add(Calendar.MINUTE, Integer.parseInt(time));
		return df.format(Calendar.getInstance().getTime());
	}

	public static String daysAddInDate(String date, int numOfDays) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date)); // Now use today date.
		c.add(Calendar.DATE, numOfDays); // Adding 5 days
		return sdf.format(c.getTime());
	}

	public static String getDayFromDate(String str_date) {
		DateFormat formatter;
		Date date = null;
		SimpleDateFormat simpleDateformat = null;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date) formatter.parse(str_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		simpleDateformat = new SimpleDateFormat("EEEE");
		return simpleDateformat.format(date).toString();
	}

	public static int convertHourIntoMinute(String hourTime, boolean isReplacePeriodNotation) {
		String minutesArray[] = hourTime.split(":");
		return isReplacePeriodNotation
				? (Integer.parseInt(minutesArray[0]) * 60) + (Integer.parseInt(
						(minutesArray[1].replace("PM", "").replace("AM", "").replace("a", "").replace("p", ""))
								.trim()))
				: (Integer.parseInt(minutesArray[0]) * 60) + (Integer.parseInt(minutesArray[1]));
	}

	public static String getMonthFullNameWithIndex(String month) throws ParseException {
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM");
		return monthDisplay.format(monthParse.parse(month));
	}

	public static String getHWFormatTimeBasedOnTimeZone(String testDataText) {
		String result = "";
		DateTime dateTimeUtc = new DateTime(new java.util.Date(), DateTimeZone.UTC);
		DateTimeZone timeZone = DateTimeZone.forID(testDataText.split("_")[0]);
		LocalDateTime dt = dateTimeUtc.withZone(timeZone).toLocalDateTime();
		String[] tokens = null;
		if (testDataText.contains("+")) {
			tokens = testDataText.split("\\+")[1].split(":");
		} else if (testDataText.contains("-")) {
			tokens = testDataText.split("\\-")[1].split(":");
		} else {
			return FDConstants.HW_TIME_FORMAT_OBJECT.format(dt.toDate());

		}
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		int durationInMinutes = (60 * hours) + minutes;
		try {
			if (testDataText.toLowerCase().contains("_hw_time_zone_time")) {
				if (testDataText.contains("+")) {
					result = dt.plusMinutes(durationInMinutes).toString(FDConstants.HW_TIME_PATTERN);
				}
				if (testDataText.contains("-")) {
					result = dt.minusMinutes(durationInMinutes).toString(FDConstants.HW_TIME_PATTERN);
				}
				if (result.equals("")) {
					result = FDConstants.HW_TIME_FORMAT_OBJECT.format(dt.toDate());
				}
			}
		} catch (Exception e) {
			return testDataText;
		}
		return result.replace(" PM", "p").replace(" AM", "a");
	}

	public static String decimalFormat(float decimalNumber, int frac) {
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(frac);
		return decimalFormat.format(decimalNumber);
	}

	public static int convertHourToMinute(String hourTime) {
		String minutesArray[] = hourTime.split(":");
		return (Integer.parseInt(minutesArray[0]) * 60) + (Integer.parseInt(minutesArray[1]));
	}

	public static float convertMinuteToHour(int minutes) {
		int hours = minutes / 60 + Integer.parseInt("00:00".substring(0, 1));
		int min = minutes % 60 + Integer.parseInt("00:00".substring(3, 4));
		return Float.parseFloat(hours + "." + min);
	}

	public static String getTimeInMilitary(String time) throws ParseException {
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mma");
		Date date = parseFormat.parse(getDateFormat(time));
		return displayFormat.format(date);
	}

	private static String getDateFormat(String string) {
		if (string.contains(" ")) {
			String str[] = string.split(" ");
			System.out.println(str[0] + str[1]);
			return str[0] + str[1];
		}
		return string;
	}

	public static String incrementDateByNumOfDays(String date, String format, int numOfDays) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat.parse(date.replace("/", "-")));
		cal.add(Calendar.DATE, numOfDays);
		String convertedDate = dateFormat.format(cal.getTime());
		return convertedDate;
	}

	public static String getWeekName(int _val) {
		switch (_val) {
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thrusday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		}
		return "Invalid Entry.";
	}

	public static double getValueInRoundFigure(double value) {
		return Math.round(value);
	}

	public static String getIntoDecimalFormat(double decimalNumber, int fraction) {
		return String.format("%." + fraction + "f", decimalNumber);
	}

}