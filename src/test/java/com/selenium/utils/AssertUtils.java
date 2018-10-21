/**
 * 
 */
package com.selenium.utils;

import java.util.Collection;
import java.util.Map;

import org.testng.Assert;

/**
 * @author 
 *
 */
public class AssertUtils {

	public static void isEmptyString(final String aString) {
		if(aString.isEmpty())
			throw new NullPointerException("The passed string cannot be empty.");
	}

	public static void isEmptyString(final String aString, String exceptionMessage) {
		if(aString.isEmpty()) {
			throw new IllegalArgumentException(exceptionMessage);
		}
	}

	public static void isNull(final Object anObject) {
		if(anObject==null)
			throw new NullPointerException("The passed argument cannot be null.");
	}

	public static void isNull(final Object anObject, String exceptionMessage) {
		if(anObject==null) {
			throw new NullPointerException(exceptionMessage);
		}
	}



	public static void isNullOrEmptyMap(final Map<?, ?> anObject, String exceptionMessage) {
		if(anObject == null || anObject.isEmpty()) {
			throw new NullPointerException(exceptionMessage);
		}
	}

	public static <T> void isNullOrEmptyCollection(final Collection<T> anObject, String exceptionMessage) {
		if(anObject == null || anObject.isEmpty()) {
			throw new NullPointerException(exceptionMessage);
		}
	}

	public static <T> T checkState(final boolean checkState, final T reference, String exceptionMessage) {
		if(!(checkState)) {
			throw new IllegalStateException(exceptionMessage);
		}
		return reference;
	}
	
	public static void validateResult(boolean flag) {
        boolean result = true;
        try {
            Assert.assertEquals(true, flag);
        } catch(AssertionError e) {
            result = false;
        }
        LoggerUtils.printInfo("The Case Has Been : "+result);
    }
    
}