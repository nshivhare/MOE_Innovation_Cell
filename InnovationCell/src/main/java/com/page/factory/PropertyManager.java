package com.page.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


import com.utilities.Logg;

public class PropertyManager {

	private static final Properties PROPERTY = new Properties();
	private static final Properties DATAPROPERTY = new Properties();
	private static final String APPLICATIONPROPERTIESPATH = "/src/test/java/com/test/MoeInnovationCell/";
	private static final Logger LOGGER = Logg.createLogger();
	public static String Propertyfilename;

	private PropertyManager() {
	}

	public static Properties loadApplicationPropertyFile() {	    	
		if (System.getProperty("environment") != null) {
			Propertyfilename = System.getProperty("environment");
		} 
		else {
			Propertyfilename = "application.properties";
		}
		LOGGER.info("In PropertyManager-ApplicationPropertyFile, name of property file is " + Propertyfilename);
		try {
			PROPERTY.load(new FileInputStream(System.getProperty("user.dir")
					+ APPLICATIONPROPERTIESPATH + Propertyfilename));            
		} catch (IOException io) {
			LOGGER.info(
					"IOException in the loadApplicationPropertyFile() method of the PropertyManager class",
					io);
		}
		return PROPERTY;
	}

	// Method is to read the patient data studies and various inputs from property file
	public static Properties loadDataPropertyFile() {
		try {
			DATAPROPERTY.load(new FileInputStream(System.getProperty("user.dir")
					+ APPLICATIONPROPERTIESPATH + "patientdata.properties"));
		} catch (IOException io) {
			LOGGER.info(
					"IOException in the loadDataPropertyFile() method of the PropertyManager class",
					io);
		}
		return DATAPROPERTY;
	}

	
	public static String getBrowser(Properties prop){
		String browser = null;

		if (System.getProperty("browser") != null) {
			browser = System.getProperty("browser");
		} else {
			browser = prop.getProperty("browserName");
		}
		LOGGER.info("In PropertyManager.getBrowser, browser to be launched is " + browser);
		return browser;
	}
}
