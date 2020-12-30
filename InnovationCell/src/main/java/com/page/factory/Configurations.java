package com.page.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Properties;

import com.relevantcodes.extentreports.LogStatus;

public class Configurations {

	private static final Properties APPLICATIONPROPERTY = PropertyManager.loadApplicationPropertyFile();
	public static HashMap<String, String> TEST_PROPERTIES = new HashMap<String,String>();
	public static LogStatus INFO = LogStatus.INFO;
	public static LogStatus WARNING = LogStatus.WARNING;
	public static LogStatus PASS = LogStatus.PASS;
	public static LogStatus FAIL = LogStatus.FAIL;
	public static LogStatus SKIP = LogStatus.SKIP;
	public static final String YES = "YES";
	public static final String NO = "NO";

}
