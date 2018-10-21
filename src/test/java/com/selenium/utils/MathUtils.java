package com.selenium.utils;

import java.text.DecimalFormat;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.openqa.selenium.Point;

public class MathUtils {

	public static String evaluate(String expression) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		Object result = null;
		try {
			result = engine.eval(expression);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (result != null ? result.toString() : "");
	}

	public static int takeDigits(String value) {
		String temp = "";
		int index;
		temp = value.replaceAll("[\\sa-zA-Z+-]", "");
		index = Integer.parseInt(temp);
		return index;
	}

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("#.00");
		// String result = df.format("1.7491166077738516");
		System.out.println(df.format(1.7491166077738516));
	}

	/*
	 * Calculate Coordinate of minute point on circumference of a clock :
	 * formulae Where the centre of the circle is (X0, Y0), the radius is R and
	 * the angle with the x-axis is theta:
	 * 
	 * X1 = (+-R * cos theta) + X0 and
	 * 
	 * Y1 = (+-R * sin theta) + Y0 P(X1,Y1) is the point that can be clicked
	 * using TouchActions
	 */
	public static Point getPointOnCircumference(Point center, int radius, int minutes, int degrees) {
		return new Point((int) (1 * radius * Math.sin(Math.toRadians(minutes * degrees)) + center.getX()),
				((int) (-1 * radius * Math.cos(Math.toRadians(minutes * degrees)) + center.getY())));
	}

}
