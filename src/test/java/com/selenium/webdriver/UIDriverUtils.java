package com.selenium.webdriver;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;


@SuppressWarnings("unused")
public class UIDriverUtils {

    private UIDriverUtils() {
	;
    }


    public static final String BY_XPATH = "BY XPATH";
    public static final String BY_ID = "BY ID";
    public static final String BY_CLASSNAME = "BY CLASS_NAME";
    public static final String BY_NAME = "BY NAME";
    public static final String BY_LINKTEXT = "BY LINK TEXT";
    public static final String BY_TAGNAME = "BY TAGNAME";
    public static final String BY_PARTIAL_LINK_TEXT = "BY PARTIAL LINK TEXT";
    public static final String BY_CSS_SELECTOR = "BY CSS SELECTOR";
    public static final String BY_ACCESSIBILITY_ID = "BY ACCESSIBILITY ID";
    private static int timeoutTrialsCount;

    // to click on ViewMore Button from # App-Market
    public static By clickApplicationOnAppMarket(String zipAppID) {
	return createByObjectForByIID(BY_XPATH, "//a[contains(@ng-click," + zipAppID + ")]");
    }

    public static By clickAppPageImage(String moduleName) {
	return createByObjectForByIID(BY_XPATH, "//img[contains(@ng-src,'" + moduleName + "')]");
    }



  

    public static By createByObjectForByIID(String byIID, String byIDExpression) {
	switch (byIID) {
	case BY_XPATH:
	    return By.xpath(byIDExpression);
	case BY_ID:
	    return By.id(byIDExpression);
	case BY_NAME:
	    return By.name(byIDExpression);
	case BY_LINKTEXT:
	    return By.linkText(byIDExpression);
	case BY_CLASSNAME:
	    return By.className(byIDExpression);
	case BY_TAGNAME:
	    return By.tagName(byIDExpression);
	case BY_PARTIAL_LINK_TEXT:
	    return By.partialLinkText(byIDExpression);
	case BY_CSS_SELECTOR:
	    return By.cssSelector(byIDExpression);
	case BY_ACCESSIBILITY_ID:
	    return MobileBy.AccessibilityId(byIDExpression);
	default:
	    throw new RuntimeException("Unknown Type '" + byIID + "'");
	}
    }

    public static String translateWebText(String webText) {
	return webText.replace("&gt;", ">").replace("amp;", "");
    }
}