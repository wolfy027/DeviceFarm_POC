package com.selenium.utils;

public class ReflectionUtils {

	public static String methodName(){
		return new Exception().getStackTrace()[1].getMethodName();
	}
}
