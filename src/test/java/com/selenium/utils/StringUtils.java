package com.selenium.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class StringUtils {

	/**
	 * Calculates the similarity (a number within 0 and 1) between two strings.
	 */
	public static double similarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater
			// length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0; /* both strings are zero length */
		}
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}

	public static <T> int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	public static String getMapAsFormattedString(HashMap<? extends Object, ? extends Object> hashMap) {
		StringBuilder outputBuilder = new StringBuilder();
		Set<? extends Object> keys = hashMap.keySet();
		Iterator<? extends Object> iterator = keys.iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			outputBuilder.append("\t" + key + "\t\t:\t\t" + hashMap.get(key) + "\n");
		}
		return outputBuilder.toString();
	}

	public static String numberOnly(String input) {
		return input.replaceAll("[^0-9.]", "");
	}

	public static String getWOTCFromSSN(String ssn, String prefix, String exemptStatus) {
		return getWOTC(ssn, prefix, exemptStatus);
	}

	public static String getWOTC(String socialSecurityNumber, String prefix, String exemptStatus) {
		if (socialSecurityNumber.length() != 9)
			return "ssn not of apt length";
		int SSN = Integer.valueOf(socialSecurityNumber);
		int SSN1 = SSN / 1000000;
		int SSN3 = SSN % 1000;
		String SSN2 = socialSecurityNumber.substring(3, 6);
		SSN1 = SSN1 + 345;
		if (SSN1 >= 1000)
			SSN1 = SSN1 - 1000;
		SSN2 = SSN2.substring(0, 1) + SSN2.substring(2, 3) + SSN2.substring(1, 2);
		SSN3 = SSN3 - 42;
		if (SSN3 < 0)
			SSN3 = SSN3 + 1000;
		return prefix + org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(SSN1), 3, "0")
				+ org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(SSN2), 3, "0")
				+ org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(SSN3), 3, "0") + exemptStatus;
	}

}
