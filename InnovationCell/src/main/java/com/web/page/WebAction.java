package com.web.page;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import java.util.List;

import java.util.Set;
import java.util.logging.Level;


import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;

import com.google.common.base.Function;
import com.page.factory.Configurations;
import com.page.factory.MoeInnovationConstant;

import com.utilities.Logg;
import com.utilities.Utilities;


import static com.page.factory.Configurations.TEST_PROPERTIES;


public class WebAction {
	protected static final Logger LOGGER = Logg.createLogger();
	protected WebDriver driver;
	private WebDriver WEB_DRIVER = null ;
	private static final String SKIPLOGIN = "skipLogin";
	private String APPLICATIONURL;

	private String APPLICATIONURLUSINGUSER;
	protected static final String ELEMENTSEARCHTIMEOUT = "elementSearchTimeOut";
	private static final String ELEMENTWAITTIME = "waitForElement";
	public static String extractedFolderPath;

	public static final String VISIBILITY = "visibility";
	public static final String PRESENCE = "presence";
	public static final String CLICKABILITY = "clickability";

	private JavascriptExecutor js = null;

	public WebAction(WebDriver driver) {

		WEB_DRIVER = driver;
		this.driver = driver;


	}



	/**
	 * @author anilt
	 * @param key
	 * @param value
	 * Description: This function sets the cookie with key and value specified in arguments
	 */
	public void setCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		driver.manage().addCookie(cookie);
		LOGGER.info(Utilities.getCurrentThreadId() + "Successfully added cookie named " + key
				+ " to the HTML page");
	}

	/**
	 * @author anilt
	 * @param key - cookie name for which value is to be retrieved
	 * @return String containing cookie value
	 * Description: This function returns value for the cookie passed as argument
	 */
	public String getCookie(String key) {
		LOGGER.info(Utilities.getCurrentThreadId() + "Retrieving the value "
				+ driver.manage().getCookieNamed(key).getValue() + " stored in the cookie");
		return driver.manage().getCookieNamed(key).getValue();
	}

	/**
	 * @author abhishekr
	 * @param URL
	 * Description: This function is used to navigate to the url specified in argument
	 */
	public void navigateToURL(String URL) {
		try {

			driver.navigate().to(URL);

		} catch(Exception e) {


			Assert.assertTrue(false, "Exception in navigateToURL(): " + e.getStackTrace());
		}
	}

	public void navigateToChangedURL(String URL) {
		try {
			LOGGER.info(Utilities.getCurrentThreadId() + "Navigating to URL: " + URL);	
			// Timeout exception in latest chrome driver 2.35 hence using javascript
			//driver.navigate().to(URL);
			((JavascriptExecutor) driver).executeScript("window.open(\""+URL+"\",\"_self\");");
			LOGGER.info(Utilities.getCurrentThreadId() + "Successfully navigated to Application URL on the Local Browser");
		} catch(Exception e) {

			Assert.assertTrue(false, "Exception in navigateToURL(): " + e.getStackTrace());
		}
	}

	/**
	 * @author anilt
	 * Description: This function is used to navigate to the base url of the application
	 */
	public void navigateToBaseURL() {
		try {
			if ((TEST_PROPERTIES.get(SKIPLOGIN)).toUpperCase().equals("YES")) {
				LOGGER.info(Utilities.getCurrentThreadId() + "Navigating to Application URL on Local Browser: " + APPLICATIONURLUSINGUSER);
				driver.get(APPLICATIONURLUSINGUSER);
			} else {
				LOGGER.info(Utilities.getCurrentThreadId() + "Navigating to Application URL on Local Browser: " + APPLICATIONURL);

				if(!driver.getCurrentUrl().contains(APPLICATIONURL))
					driver.get(APPLICATIONURL);	
				waitForTimePeriod(100);
			}
			LOGGER.info(Utilities.getCurrentThreadId()	+ "Successfully navigated to Application URL on the Local Browser");
		} catch(Exception e) {

			Assert.assertTrue(false, "Exception in navigateToBaseURL(): " + e.toString());
		}
	}

	/**
	 * @author anilt
	 * @param context
	 * Description: This function closes the browser instance
	 */
	public void closeBrowser(ITestContext context) {
		driver.manage().deleteAllCookies();
		clearCache();
		try {
			driver.close();
			driver.quit();
			LOGGER.info(Utilities.getCurrentThreadId() + "Closing the browser");
			context.getAttribute(context.getCurrentXmlTest().getName());        
			LOGGER.info(Utilities.getCurrentThreadId() + "Sucessfully closed the browser" + "\n");
		} catch(Exception e) {

			Assert.assertTrue(false, "Exception in closeBrowser(): " + e.getStackTrace());
		}

	}

	public void clearCache(){
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		ChromeDriver driver = new ChromeDriver(capabilities);
	}
	/**
	 * @param syncKey
	 * @param element
	 * @param value
	 * @throws TimeoutException
	 * @throws WaitException
	 * Description: This function enters the specified value in the webelement
	 */


	public void enterText(String syncKey, WebElement element, String value) throws TimeoutException
	{

		element.click();
		LOGGER.info(Utilities.getCurrentThreadId() + "Clearing the content of the text box");
		element.clear();
		LOGGER.info(Utilities.getCurrentThreadId() + "Contents cleared");
		element.sendKeys(value);
		LOGGER.info(Utilities.getCurrentThreadId() + "Entered text:" + value
				+ " in text box with locator:" + element);
	}

	public void enterText(WebElement element, String value) throws TimeoutException
	{
		element.clear();
		LOGGER.info(Utilities.getCurrentThreadId() + "Contents cleared");
		element.sendKeys(value);
		LOGGER.info(Utilities.getCurrentThreadId() + "Entered text:" + value
				+ " in text box with locator:" + element);
	}

	public void enterTextWithView(WebElement element, String value) throws TimeoutException
	{
		if(!element.isDisplayed())
			scrollIntoView(element);
		element.clear();
		LOGGER.info(Utilities.getCurrentThreadId() + "Contents cleared");
		element.sendKeys(value);
		LOGGER.info(Utilities.getCurrentThreadId() + "Entered text:" + value
				+ " in text box with locator:" + element);
	}

	public void enterText(String value) throws TimeoutException
	{
		Actions ac = new Actions(driver);
		ac.sendKeys(value).perform();

	}


	public void clickUsingAction(WebElement element) throws TimeoutException{

		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		action.click(element).perform();
		LOGGER.info(Utilities.getCurrentThreadId() + "Clicked on element with locator:" + element);
	}
	/**
	 * @param syncKey
	 * @param element
	 * @throws TimeoutException
	 * @throws WaitException
	 * Description: Function to right click on the given element
	 */


	/**
	 * @author anilt
	 * @param element
	 * Description: Function to click on the given element using jquery
	 */
	public void clickByJQuery(String element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("$('" + element + "').click()");
		LOGGER.info("Clicked on element with locator:" + element + " using JQuery");
	}

	public boolean verifyPresenceOfVerticalScrollbar(WebElement element){

		boolean scrollBarPresent = false;
		String script = " return arguments[0].scrollHeight > arguments[0].clientHeight ;";
		scrollBarPresent = (boolean) ((JavascriptExecutor)driver).executeScript(script,element);
		return scrollBarPresent;

	}

	public boolean verifyPresenceOfHorizontalScrollbar(WebElement element){

		boolean scrollBarPresent = false;
		String script = "return arguments[0].scrollWidth > arguments[0].clientWidth;";
		scrollBarPresent = (boolean) ((JavascriptExecutor)driver).executeScript(script,element);
		return scrollBarPresent;

	}
	/**
	 * @author payala2
	 * @param element
	 * @throws null
	 * Description: Function to scroll down to the specific element
	 */
	public void scrollIntoView(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public void scrollToEnd(){
		((JavascriptExecutor) driver)
		.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}



	/**
	 * @author anilt
	 * @param element
	 * @throws Exception
	 * Description: Function to click on the given element using javascript
	 */
	public void clickByJExecutor(By element){
		WebElement ele = driver.findElement(element);
		if (ele.isEnabled() && ele.isDisplayed()) {
			LOGGER.info("Clicking on element with locator:" + ele + " using JavaExecutor");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
		} else {
			LOGGER.info("Unable to click on element with locator:" + ele + " using JavaExecutor");
		}
	}

	/**
	 * @param syncKey
	 * @param element
	 * @throws TimeoutException
	 * @throws WaitException
	 */
	
	/**
	 * @param windowTitle
	 * @throws WaitException
	 * @throws InterruptedException
	 */
	public void switchToSecondaryWindow(String windowTitle) throws  InterruptedException {
		waitForTimePeriod("High");
		Set<String> windows = driver.getWindowHandles();
		for (String strWindows : windows) {
			if (driver.switchTo().window(strWindows).getTitle().equals(windowTitle)) {
				driver.switchTo().window(strWindows);
				//				driver.switchTo().window(strWindows).manage().window().maximize();
				break;
			}
		}
	}



	 





	public String getText(WebElement element) throws TimeoutException {
		String actual = element.getText().trim();
		LOGGER.info(Utilities.getCurrentThreadId() + "Actual Value:" + actual);
		return actual;
	}

	/**
	 * @author anilt
	 * @return
	 * Description: This function returns the page title
	 */
	public String getTitle() {
		LOGGER.info(Utilities.getCurrentThreadId() + "Title of the page:" + driver.getTitle());
		return driver.getTitle();
	}




	public String getAttributeValue(WebElement webElement, String attribute) throws TimeoutException {
		LOGGER.info(Utilities.getCurrentThreadId() + "Retrieving the attribute " + attribute );
		String attributeValue= webElement.getAttribute(attribute);
		return attributeValue;
	}

	/**
	 * @param syncKey
	 * @param locator
	 * @throws TimeoutException
	 * @throws WaitException
	 */



	public String getCssValue(WebElement webElement, String property) throws TimeoutException {
		//		LOGGER.info(Utilities.getCurrentThreadId() + "Retrieving the property " + property );

		String attributeValue= webElement.getCssValue(property);
		return attributeValue;
	}


	public boolean verifyAttributePresence(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null){
				result = true;
			}
		} catch (Exception e) {}

		return result;


	}


	public String getAllAttributes(WebElement element) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Object attributes=executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", element);
		return attributes.toString();
	}

	public boolean verifyCssPropertyPresence(WebElement element, String property) {

		Boolean result = false;
		try {
			String value = element.getCssValue(property);
			if ((!value.equalsIgnoreCase("none")) && value!= null){
				result = true;
			}
		} catch (Exception e) {}

		return result;


	}



	public Boolean getVisibiltyOfElementLocatedBy(By locator)  {
		return checkForElementVisibility(locator);
	}


	public Boolean getPresenceOfElementLocatedBy(By locator) {
		return checkForElementPresence(locator);
	}


	public Boolean getClickabilityOfElementLocatedBy(By locator) {
		return checkForElementClickability( locator);
	}

	/**
	 * @param locator
	 * @return
	 * @throws WaitException
	 */
	public Boolean getInvisibilityOfElementLocatedy(By locator) {
		return waitForElementInVisibility(locator);
	}

	/**
	 * @param key
	 */
	public void pressModifierKey(Keys key) {
		Actions action = new Actions(driver);
		action.keyDown(key).perform();
	}

	/**
	 * @param key
	 */
	public void pressKeys(Keys key) {
		Actions action = new Actions(driver);
		action.sendKeys(key).moveByOffset(1, 1).build().perform();
	}

	/**
	 * @param String
	 */
	public void pressKeys(String asciiValue) {
		Actions action = new Actions(driver);
		action.sendKeys(asciiValue).perform();
	}


	public boolean takeScreenShot(String destinationFilepath){
		boolean status= false; 
		try {
			File screenshot = null;
			if(WEB_DRIVER  != null){
				waitForTimePeriod(1000);
				screenshot= ((TakesScreenshot) WEB_DRIVER).getScreenshotAs(OutputType.FILE);
				LOGGER.info(Utilities.getCurrentThreadId() + "Screenshot taken successfully. Name of file :" + destinationFilepath);
				FileUtils.copyFile(screenshot, new File(destinationFilepath));
				status= true;
			}
			else 
				LOGGER.info("WEB_DRIVER is not initialised, can't take a screeshot");
		} catch (Exception e) {
			LOGGER.error(new StringBuilder()
					.append(Utilities.getCurrentThreadId() + "Failed to capture screenshot ----: ")
					.append(e.getMessage()),e);
		}
		return status;

	}

	public WebElement getElements(By element,int index){
		List<WebElement> list =driver.findElements(element);
		LOGGER.info(Utilities.getCurrentThreadId() + "Finding the element with locator:" + element);
		return list.get(index);
	}

	public WebElement getElement(By ele){

		WebElement element = null ;
		try {
			element =  driver.findElement(ele);

		} catch (NoSuchElementException e) {
			printStackTrace(e.getMessage());
		}

		return element;
	}


	public List<WebElement> getElements(By ele){

		List<WebElement> element = new ArrayList<WebElement>() ;
		try {
			element =  driver.findElements(ele);

		} catch (NoSuchElementException e) {
			printStackTrace(e.getMessage());
		}

		return element;
	}
	


	public void mouseHoverWithClick(String syncKey, WebElement element, int xOffset,int yOffset) throws TimeoutException {
		Actions action = new Actions(driver);
		action.moveToElement(element).moveByOffset(xOffset, yOffset).click().build().perform();
		LOGGER.info(Utilities.getCurrentThreadId() + "Mouse hovered on element with locator:" + element + "and Offset");

	}
	public void mouseHoverWithClick(String syncKey, WebElement element) throws TimeoutException {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();
		LOGGER.info(Utilities.getCurrentThreadId() + "Mouse hovered on element with locator:" + element + "and Offset");

	}



	public void mouseHover(WebElement element) throws InterruptedException {

		Actions action = new Actions(driver);
		action.moveToElement(element).pause(100).build().perform();
		waitForTimePeriod(1000);
		LOGGER.info(Utilities.getCurrentThreadId() + "Mouse hovered on element with locator:" + element + "and Offset");

	}

	public void mouseHover(int xOffset, int yOffset) throws TimeoutException, InterruptedException {

		Actions action = new Actions(driver);
		action.moveByOffset(xOffset, yOffset).perform();
		LOGGER.info(Utilities.getCurrentThreadId() + "Mouse hovered on x offset="+ xOffset+ " y offset="+yOffset);

	}
	
	


	public void clickAndHoldRelease(int xOffset, int yOffset) throws InterruptedException {
		Actions action = new Actions(driver);

		action.clickAndHold().perform();
		LOGGER.info("drag and drop...clickAndHold");

		action.moveByOffset(xOffset, yOffset).perform();
		waitForTimePeriod(100);
		LOGGER.info("drag and drop...moveByOffset");

		//		action.release().build().perform();
		action.release().perform();
		waitForTimePeriod(1000);
		action.moveByOffset(10, 10).perform();
		LOGGER.info("drag and drop...completed");

	}

	public void clickAndHoldReleaseQuick(int xOffset, int yOffset) throws InterruptedException {
		Actions action = new Actions(driver);

		action.clickAndHold().perform();
		LOGGER.info("drag and drop...clickAndHold");

		action.moveByOffset(xOffset, yOffset).perform();
		LOGGER.info("drag and drop...moveByOffset");

		action.release().build().perform();
		LOGGER.info("drag and drop...completed");

	}

	
	public void clickAt(int xOffset,int yOffset) throws TimeoutException, InterruptedException {
		String browserName=Configurations.TEST_PROPERTIES.get("browserName");
		Actions action = new Actions(driver);
		action.moveByOffset(xOffset, yOffset).build().perform();
		//		waitForTimePeriod("Low");
	
			action.click().build().perform();
		}

	
	public void doubleClick() {
		Actions action = new Actions(driver);
		action.doubleClick().build().perform();
	}

	public void doubleClick(WebElement element) {
		Actions action = new Actions(driver);
		action.doubleClick(element).build().perform();
	}

	
	public void DoubleClickWithMouseHover(WebElement element) throws TimeoutException, InterruptedException{
		Actions action = new Actions(driver);
		mouseHover(element);
		action.doubleClick().build().perform();
	}

	
	public void renameFiles(String testCaseName) {
		try{
			Utilities.renameFile(TEST_PROPERTIES.get("extractedFileFolderPath"), testCaseName);
		}catch(Exception e){
			LOGGER.error(Utilities.getCurrentThreadId()+"Exception in renameFiles()"+e.getStackTrace(),e);
			Assert.assertTrue(false,"Exception in renameFiles() "+e.toString());
		}
	}

	/**
	 * @author anilt
	 * Description: This function returns the baseurl
	 */
	public String getBaseURL() {
		if ((TEST_PROPERTIES.get(SKIPLOGIN)).toUpperCase().equals("YES")) {
			return APPLICATIONURLUSINGUSER;
		} else {
			return APPLICATIONURL;	
		}
	}

	/**
	 * @author anilt
	 * @param element
	 * @return
	 * Description: Function to verify if given element is enabled
	 */
	public boolean verifyButtonEnabled(WebElement element) {
		boolean status= false;
		if(element.isEnabled()){
			status = true;
		}else{
			status = false;
		}
		return status;
	}

	/**
	 * @author chiranjivb
	 * @param locator - locator of element whose presence is to be checked
	 * @return true if element is present
	 * Description: This function checks whether the given element is present in dom without waiting for any specific time for the element to load
	 */
	public boolean checkForElementPresenceWithoutWaitTime(By locator) {
		try {
			driver.findElement(locator);
			LOGGER.info(Utilities.getCurrentThreadId()+ "WebElement Present. Proceeding further...");
			return true;
		} catch (Exception e) {
			LOGGER.info("Element not found by locator "+locator);
			return false;
		}
	}
	/**
	 * @author chiranjivb
	 * @param protocolName - name of the protocol
	 * @param locator - Locator of the element to capture image
	 * @param checkpoint - Test protocol checkpoint
	 * @param imageName - Name of the captured image
	 * @param coordinates - coordinates to crop image
	 * Description : This function compares images of the provided element with baseline image after cropping the images by specified coordinates
	 * @throws InterruptedException 
	 */

	/**
	 * @author abhishekr
	 * @param coOrdinateValue
	 * @param expectedPart
	 * Description : Functions used to convert pixels into small intervals
	 * @return
	 */


	public void assertEquals(Set<String> set, Set<String> set2,String action, String message){

		Assert.assertEquals(set,set2,"Expected value="+set2+" but Found value="+set);

	}

	public void assertEquals(List list, List list2,String action, String message){

		Assert.assertEquals(list,list2,"Expected value="+list2+" but Found value="+list);

	}

	public void assertEquals(HashMap<String, String> map, HashMap<String, String> map2,
			String action, String message) {
		Assert.assertEquals(map,map2,"Expected value="+map2+" but Found value="+map);

	}


	//nikita: Description: to verify value on both UI and database after update in DB


	public void assertEquals(boolean actual, boolean expected,String action, String message){

		Assert.assertEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);


	}
	public void assertEquals(String actual, String expected,String action, String message){

		Assert.assertEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);


	}
	public void verifyEquals(String actual, String expected,String action, String message){

		try{
			Assert.assertEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);

		}catch(AssertionError error)
		{

		}


	}

	public void verifyTrue(boolean condition,String action, String message){

		try{
			Assert.assertTrue(condition,"Condition is:="+condition);

		}catch(AssertionError error)
		{

		}



	}
	public void verifyFalse(boolean condition,String action, String message){

		try{
			Assert.assertFalse(condition,"Condition is:="+condition);

		}catch(AssertionError error)
		{

		}



	}

	public void verifyEquals(int actual, int expected,String action, String message){

		try{
			Assert.assertEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);

		}catch(AssertionError error)
		{

		}



	}

	public void verifyEquals(Boolean actual, Boolean expected,String action, String message){

		try{
			Assert.assertEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);

		}catch(AssertionError error)
		{

		}


	}

	public void assertEquals(int actual, int expected,String action, String message){

		Assert.assertEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);


	}

	public void assertEquals(double actual, double expected,String action, String message){

		Assert.assertEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);


	}

	public void assertNotEquals(double actual, double expected,String action, String message){

		Assert.assertNotEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);


	}


	public void assertNotEquals(String actual, String expected,String action, String message){

		Assert.assertNotEquals(actual,expected ,"Expected value="+expected+" but Found value="+actual);

	}

	public void assertNotEquals(int actual, int expected,String action, String message){

		Assert.assertNotEquals(actual,expected,"Expected value="+expected+" but Found value="+actual);


	}

	public void assertTrue(boolean condition,String action, String message){

		Assert.assertTrue(condition,"Condition is:="+condition);

	}

	public void assertNull(Object object,String action, String message){

		Assert.assertNull(object,"Object is not null");


	}

	public void assertNotNull(Object object,String action, String message){

		Assert.assertNotNull(object,"Object is not null");


	}

	public void assertFalse(boolean condition,String action, String message){
		Assert.assertFalse(condition,"Condition is:="+condition);


	}

	/**
	 *  Description : Method used to open new browser window
	 */

//	public void openNewWindow(){
//
//		String browserName=Configurations.TEST_PROPERTIES.get("browserName");
//		js = (JavascriptExecutor) driver; 
//		if(browserName.equalsIgnoreCase(BrowserType.CHROME.getBrowserValue())) {
//
//			js.executeScript("window.open();");
//
//		} else		
//			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
//
//
//
//	}
//
//	public void openNewWindow(String newURL) throws InterruptedException{
//
//		String browserName=Configurations.TEST_PROPERTIES.get("browserName");
//		js = (JavascriptExecutor) driver; 
//		if(browserName.equalsIgnoreCase(BrowserType.CHROME.getBrowserValue())||browserName.equalsIgnoreCase(BrowserType.EDGE.getBrowserValue())){
//			js.executeScript("window.open(\""+newURL+"\").focus();");
//			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
//			driver.switchTo().window(tabs.get(1));
//			waitForTimePeriod("Low");
//		}
//
//		else  {      
//			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
//			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
//			driver.switchTo().window(tabs.get(1));
//			waitForTimePeriod("Low");
//			driver.get(newURL);
//		}
//
//	}

	/**
	 * Description : Method to get the current window id
	 * @return 
	 */

	public String getCurrentWindowID(){

		return driver.getWindowHandle();
	}

	public Set<String> getAllOpenedWindowsID(){


		return driver.getWindowHandles();
	}

	public List<String> getAllOpenedWindowsIDs(){

		Set<String> allWindowHandles = driver.getWindowHandles();

		return new ArrayList<String>(Arrays.asList(allWindowHandles.toArray(new String[allWindowHandles.size()])));

	}

	/**
	 * 
	 * @return : return the monitor's resolution and its width as rectangle
	 */



	public void setWindowSize(String windowID, Dimension dimension){
		driver.switchTo().window(windowID);
		driver.manage().window().setSize(dimension);
	}

	public void setWindowPosition(String windowID, Point location){


		//		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.switchTo().window(windowID);

		if(TEST_PROPERTIES.get("Browser").equalsIgnoreCase("chrome"))
			driver.manage().window().setPosition(location);
		else if(TEST_PROPERTIES.get("Browser").equalsIgnoreCase("firefox"))
		{
			js.executeScript("window.moveTo("+location.getX()+",0);");
		}

		//		driver.manage().window().setPosition(location);
	}

	/**
	 * @param windowID
	 * @throws WaitException
	 * @throws InterruptedException
	 */
	public void switchToWindow(String windowID) throws  InterruptedException {

		waitForTimePeriod("Low");
		Set<String> windows = driver.getWindowHandles();
		for (String strWindows : windows) {
			if (strWindows.equalsIgnoreCase(windowID)){
				driver.switchTo().window(windowID);
				waitForTimePeriod("Low");
				break;
			}
		}
	}

	public Point getWindowPosition(String windowID){

		driver.switchTo().window(windowID);

		return driver.manage().window().getPosition();

	}

	public void maximizeWindow(){

		driver.manage().window().maximize();
	}

	public Dimension getWindowSize(String windowID){

		driver.switchTo().window(windowID);

		return driver.manage().window().getSize();

	}

	public void closeWindow(String windowID){

		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.switchTo().window(windowID);
		if(TEST_PROPERTIES.get("Browser").equalsIgnoreCase("chrome"))
			js.executeScript("window.close();");
		else if(TEST_PROPERTIES.get("Browser").equalsIgnoreCase("firefox"))
			driver.close();



	}

	/*
	 * Wait Methods
	 * 
	 */

	public WebElement waitForElementVisibility(WebDriver driver, By locator)
			throws TimeoutException {
		try {
			WebElement element = null;
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the visibility of the element using By class:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Present and Visible. Proceeding further...");
			return element;
		} catch (TimeoutException tm) {
			throw new TimeoutException(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the visibility of the element using By class:"
					+ locator + "\n", tm);
		}
	}

	public WebElement waitForElementVisibility(By locator)
			throws TimeoutException {
		try {
			WebElement element = null;
			LOGGER.info(Utilities.getCurrentThreadId()+ "Waiting for the visibility of the element using By class:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Present and Visible. Proceeding further...");
			return element;
		} catch (TimeoutException tm) {
			throw new TimeoutException(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the visibility of the element using By class:"
					+ locator + "\n", tm);
		}
	}



	public Boolean waitForElementInVisibility( By locator)
			throws TimeoutException {
		try {
			Boolean element = true;
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the invisibility of the element using By class:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			element = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Invisible. Proceeding further...");
			return element;
		} catch (TimeoutException tm) {
			throw new TimeoutException(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the invisibility of the element using By class:"
					+ locator + "\n", tm);
		}
	}

	public Boolean waitForElementInVisibility(WebElement locator)
			throws TimeoutException {
		try {
			Boolean element = true;
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the invisibility of the element using By class:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			element = wait.until(ExpectedConditions.invisibilityOf(locator));

			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Invisible. Proceeding further...");
			return element;
		} catch (TimeoutException tm) {
			throw new TimeoutException(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the invisibility of the element using By class:"
					+ locator + "\n", tm);
		}
	}


	public Boolean waitForElementInVisibility(WebElement locator, long timeInSec)
			throws TimeoutException {
		try {
			Boolean element = true;
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the invisibility of the element using By class:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,timeInSec);

			element = wait.until(ExpectedConditions.invisibilityOf(locator));

			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Invisible. Proceeding further...");
			return element;
		} catch (TimeoutException tm) {
			throw new TimeoutException(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the invisibility of the element using By class:"
					+ locator + "\n", tm);
		}
	}

	private WebElement waitForElementPresence(By locator)
			throws TimeoutException {
		try {
			WebElement element = null;            
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the presence of the element using By class:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));            
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Present. Proceeding further...");
			return element;
		} catch (TimeoutException tm) {
			throw new TimeoutException(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the presence of the element using By class:"
					+ locator + "\n", tm);
		}
	}

	public WebElement waitForElementVisibility(WebElement beforeVisibilityElement)
			throws TimeoutException {
		try {
			WebElement afterVisibilityElement = null;
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the visibility of the web element:" + beforeVisibilityElement);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			afterVisibilityElement = wait.until(ExpectedConditions
					.visibilityOf(beforeVisibilityElement));
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Visible. Proceeding further...");
			return afterVisibilityElement;
		} catch (TimeoutException tm) {
			throw new TimeoutException(Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the visibility of the web element:"
					+ beforeVisibilityElement + "\n", tm);
		}
	}







	public void waitForTimePeriod(int timeOut) throws InterruptedException {
		try {
			LOGGER.info(Utilities.getCurrentThreadId() + "Thread.sleep activated for " + timeOut/ 1000 + " seconds");
			Thread.sleep(timeOut);
			LOGGER.info(Utilities.getCurrentThreadId() + "Ended after waiting for " + timeOut
					/ 1000 + " seconds");
		} catch (InterruptedException ie) {
			throw new InterruptedException(
					"Thread Interrupted Exception in the waitForTimePeriod() method of WebDriverWaits class");
		}
	}

	public void waitForTimePeriod(String time) throws InterruptedException {
		int timeOut=0;
		if(time.equalsIgnoreCase("Low")){
			timeOut=Integer.parseInt(Configurations.TEST_PROPERTIES.get("waitForElementLowTime"));
			waitForTimePeriod(timeOut);
		}else if (time.equalsIgnoreCase("Medium")) {
			timeOut=Integer.parseInt(Configurations.TEST_PROPERTIES.get("waitForElementMediumTime"));
			waitForTimePeriod(timeOut);
		}else if (time.equalsIgnoreCase("High")) {
			timeOut=Integer.parseInt(Configurations.TEST_PROPERTIES.get("waitForElementHighTime"));
			waitForTimePeriod(timeOut);
		}else {
			timeOut=Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTWAITTIME));
			waitForTimePeriod(timeOut);
		}						
	}

	public Boolean checkForElementVisibility( final By locator)
	{
		try {
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Checking for the visibility of the element using By class:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return driver.findElement(locator).isDisplayed();
				}
			});
			LOGGER.info(Utilities.getCurrentThreadId() + "Element visible.");
			return true;
		} catch (TimeoutException tm) {
			LOGGER.error(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the visibility of the element using By class:"
					+ locator + "\n", tm);
			return false;
		}
	}

	public Boolean checkForElementPresence( By locator)
			throws TimeoutException {
		try {
			WebElement element = null;            
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the presence of the element using By class:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));            
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Present. Proceeding further...");
			return true;
		} catch (NoSuchElementException | TimeoutException tm) {
			return false;
		}
	}

	public Boolean checkForTextPresenceInElement(WebElement locator, String text) throws TimeoutException {
		try {
			Boolean element = true;
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the presence of Text in Element value:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			element = wait.until(ExpectedConditions.textToBePresentInElement(locator, text));

			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Visible. Proceeding further...");
			return element;
		} catch (TimeoutException tm) {
			throw new TimeoutException(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for the presence of Text in Element:"
					+ locator + "\n", tm);
		}
	}

	public Boolean checkForElementClickability( By locator)
			throws TimeoutException {
		try {
			WebElement element = null;
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Waiting for the clickability of the element:" + locator);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "WebElement Clickable. Proceeding further...");
			return true;
		} catch (TimeoutException tm) {
			return false;
		}
	}






	/**
	 * Method wait for all Ajax Calls end or for GlobalTimeOut seconds
	 * TODO : waitForEndOfAllAjaxes method is incomplete
	 */
	public void waitForEndOfAllAjaxes(){
		LOGGER.info(Utilities.getCurrentThreadId()
				+ " Wait for the page to load...");
		WebDriverWait wait = new WebDriverWait(driver,Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				//				return (Boolean)((JavascriptExecutor)driver).executeScript("return jQuery.active == 0");
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
				//				 return Boolean.valueOf(((JavascriptExecutor) driver).executeScript("return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)").toString());

			}
		});

	}

	public void click(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(element));
		ele.click();

	}

	public void click(int xOffset, int yOffset)
	{

		Actions action = new Actions(driver);
		action.moveByOffset(xOffset, yOffset).click().build().perform();

	}


	public void click(String element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(element)));
		ele.click();
	}

	public void clickUsingJavaScript(WebElement element){

		JavascriptExecutor executor = (JavascriptExecutor)driver;

		executor.executeScript("arguments[0].click();", element);


	}

	public String getCurrentPageURL(){

		return driver.getCurrentUrl();
	}

	public void refreshWebPage() throws InterruptedException{

		driver.navigate().refresh();
		waitForTimePeriod(3000);
	}

	public void browserBackWebPage(){

		driver.navigate().back();
	}

	public void browserForwardWebPage(){

		driver.navigate().forward();
	}




	public void doubleClickJs(WebElement element) 
	{
		String jScript = "if(document.createEvent)"
				+ "{var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent('dblclick',+true, false);arguments[0].dispatchEvent(evObj);"
				+ "}"
				+"else if(document.createEventObject)"
				+ "{ arguments[0].fireEvent('ondblclick');}";
		js = (JavascriptExecutor)driver;
		js.executeScript(jScript, element );
	}

   

	public void zoomOut()  { 
		js.executeScript("document.body.style.zoom='50%'");

	}

	public void performMouseRightClick(WebElement ele){

		Actions a = new Actions(driver);
		a.moveToElement(ele).contextClick().pause(100).build().perform();


	}


	public void performMouseRightClick(WebElement viewbox ,int xOffset, int yOffset){

		Actions a = new Actions(driver);
		a.moveToElement(viewbox).moveByOffset(xOffset, yOffset).contextClick().build().perform();

	}

	public boolean isElementPresent(By ele){
		boolean status = false;
		try{
			driver.findElement(ele);
			status= true;

		}catch(NoSuchElementException e){
			status= false;
		}

		return status;
	}


	public void navigateToBack() {
		driver.navigate().back();
	}

	public void navigateToForward() {
		driver.navigate().forward();
	}

	/**
	 * @author payala2
	 * @param Number1 and Number2
	 * @return: null
	 * Description: Moving mouse to specific co-ordinates
	 * @throws AWTException 
	 */
	public void mouseMove(int Num1, int Num2) throws AWTException  {
		Robot robo = new Robot();
		robo.mouseMove(Num1, Num2);
	}

	public void leftClick() throws AWTException  {
		Robot robot = new Robot();
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click	
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click	

	}


	

	public boolean isElementPresent(WebElement ele) {

		boolean status = false;
		try{
			if(ele.isDisplayed())
				status= true;

		}catch(NoSuchElementException | NullPointerException e  ){
			status= false;
		}

		return status;
	}





	public boolean isElementPresent(WebElement element, String id){
		try{
			element.findElement(By.id(id));
		}catch(NoSuchElementException e){
			return false;
		}
		return true;
	}


	/**
	 * @author ssravanth
	 * @param Integer value of number of windows
	 * @return: boolean
	 * Description: Introduces wait time until total windows equals input parameter
	 */

	public Boolean waitForNumberOfWindowsToEqual(final int numberOfWindows)
	{
		try {
			LOGGER.info(Utilities.getCurrentThreadId()
					+ "Checking for number of windows to equal:" + numberOfWindows);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));

			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return (driver.getWindowHandles().size() == numberOfWindows);
				}
			});
			LOGGER.info(Utilities.getCurrentThreadId() + "Number of windows equals:"+numberOfWindows);
			return true;
		} catch (TimeoutException tm) {
			LOGGER.error(
					Utilities.getCurrentThreadId()
					+ "Time Out Exception while waiting for number of windows to be equal to:"
					+ numberOfWindows + "\n", tm);
			return false;
		}
	}

	/**
	 * @author payala2
	 * @param Exception message
	 * @return: warning while exception is catch
	 * Description: This method return exception warning in report
	 */
	public void printStackTrace(String e){
		LOGGER.error(e);
	}

	protected void pressF5Key(){

		JavascriptExecutor executor = (JavascriptExecutor) driver;

		executor.executeScript("location.reload()");

		waitForEndOfAllAjaxes();

	}


	public boolean isCSSValuePresent(WebElement element, String cssValue) {
		Boolean result = false;
		try {
			String value = element.getCssValue(cssValue);
			if (value != null){
				result = true;
			}
		} catch (Exception e) {
			printStackTrace(e.getMessage());
		}

		return result;
	}


	public void resizeBrowserWindow(int x, int y){
		Dimension n = new Dimension(x,y);  
		driver.manage().window().setSize(n);
		try {
			waitForTimePeriod(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// This method validates whether element is selected

	public boolean isSelected(WebElement element){

		return element.isSelected();

	}



	public boolean isElementSelected(WebElement ele){

		boolean status = false;
		try{
			if(ele.isSelected())
				status= true;

		}catch(NoSuchElementException e){
			status= false;
		}

		return status;
	}


	



	public String getTextForPage()
	{  
		LOGGER.info(Utilities.getCurrentThreadId() + "Getting the text of entire page"); 
		String result=driver.getPageSource();
		return result;
	}


	public void switchToNewWindow(int windowNumber) 
	{
		Set < String > s = driver.getWindowHandles();   
		Iterator < String > ite = s.iterator();
		int i = 1;
		while (ite.hasNext() && i < 10) {
			String popupHandle = ite.next().toString();
			driver.switchTo().window(popupHandle);
			LOGGER.info(Utilities.getCurrentThreadId() + "Window title is : "+driver.getTitle());
			if (i == windowNumber) break;
			i++;
		}
	}

	public boolean isElementEditable(WebElement element)
	{  
		boolean status=false;
		String temp = element.getCssValue("user-select");
		status = temp.contains(MoeInnovationConstant.DISPLAY_NONE_VALUE);
		return status;
	}

	public String replaceSpecialCharacterFromText(String existingText, String replaceSplCharacter, String replaceWithSplCharacter) {
		return existingText.replace(replaceSplCharacter,replaceWithSplCharacter);

	}

	public int getValueOfXCoordinate(WebElement ele) {
		return ele.getLocation().getX();
	}

	public int getValueOfYCoordinate(WebElement ele) {
		return ele.getLocation().getY();
	}

	public int getHeightOfWebElement(WebElement ele) {
		return ele.getSize().height;
	}

	public int getWidthOfWebElement(WebElement ele) {
		return ele.getSize().width;
	}



	public void performMouseWheelUp(int numberOfWheels) throws IOException, InterruptedException{

		String batchFile = System.getProperty("user.dir").replace("\\", "/")+Configurations.TEST_PROPERTIES.get("mouseWheelUp");
		Runtime.getRuntime().exec(batchFile+" "+numberOfWheels);
		waitForTimePeriod(2000);
	}

	public void performMouseWheelDown(int numberOfWheels) throws IOException, InterruptedException{

		String batchFile = System.getProperty("user.dir").replace("\\", "/")+Configurations.TEST_PROPERTIES.get("mouseWheelDown");
		Runtime.getRuntime().exec(batchFile+" "+numberOfWheels);
		waitForTimePeriod(2000);
	}


	public void pressTaskSwitcher() throws AWTException{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.delay(2000);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_ALT);


	}


	public void pressTabKey() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();
	}

	/**
	 * @param key comma
	 */
	public void pressKey(String value) {
		Actions action = new Actions(driver);
		action.sendKeys(String.valueOf(value)).perform();

	}

	public LogEntries getAllJSLogs(){
		return driver.manage().logs().get(LogType.BROWSER);
	}


	public boolean isConsoleErrorPresent() {

		Boolean status=false;

		// Capture all JSerrors
		for (LogEntry error : getAllJSLogs())   
		{
			// check if the status of log is SEVERE
			if(error.getLevel().equals(Level.SEVERE)){
				status=true; 
				LOGGER.info(Utilities.getCurrentThreadId() + "Encountered Error:"+error.getMessage());

			} }

		return status;
	}	

	public List<String> getConsoleLogs() {

		List<String> logs = new ArrayList<String>();
		for (LogEntry error : getAllJSLogs())   
		{
			//			if(error.getLevel().equals(Level.INFO))
			logs.add(error.getMessage());

		} 

		return logs;
	}	

	public boolean isConsoleErrorPresent(String givenError) {

		Boolean status=false;

		for (LogEntry error : getAllJSLogs())   
		{
			// check if the status of log is SEVERE
			if(error.getLevel().equals(Level.SEVERE)){
				//Verifying the error message
				if(error.getMessage().contains(givenError))
					status=true; 
				LOGGER.info(Utilities.getCurrentThreadId() + "Encountered Error:"+error.getMessage());
			} }


		return status;
	}	  


	public void waitForConsoleLogPresent(String givenLog) {

		int call = 0;
		for (String log : getConsoleLogs())   {
			if(log.contains(givenLog)) {
				call =-1;
				break;
			}
		}
		if(call!=-1)
			waitForConsoleLogPresent(givenLog);		



	}	  
	public void waitForURLToChange() 
	{
		LOGGER.info(Utilities.getCurrentThreadId()
				+ " Wait for the image silices to change...");
		final String currentURL = getCurrentPageURL();
		try{
			WebDriverWait wait = new WebDriverWait(driver,Integer.parseInt(Configurations.TEST_PROPERTIES.get(ELEMENTSEARCHTIMEOUT)));
			wait.until(new ExpectedCondition<Boolean>() 
			{
				@Override
				public Boolean apply(WebDriver driver) {

					return (!currentURL.equalsIgnoreCase(getCurrentPageURL()));}});
		}
		catch(TimeoutException e)
		{LOGGER.info(Utilities.getCurrentThreadId() + "Encountered Error:"+e.getMessage());}
	}




	public void clearConsoleLogs() {
		JavascriptExecutor js = (JavascriptExecutor)driver;

		String script = "console.clear();";

		js.executeScript(script);
	}


	public boolean isEnabled(WebElement element){

		return element.isEnabled();

	}
	public int getXCoordinate(WebElement element){

		Point p = element.getLocation();
		return p.getX();


	}

	public int getYCoordinate(WebElement element){

		Point p = element.getLocation();
		return p.getY();



	}

	public String toTitleCase(String str) {

		if(str == null || str.isEmpty())
			return "";

		if(str.length() == 1)
			return str.toUpperCase();

		//split the string by space
		String[] parts = str.split(" ");

		StringBuilder sb = new StringBuilder( str.length() );

		for(String part : parts){

			if(part.length() > 1 )                
				sb.append( part.substring(0, 1).toUpperCase() )
				.append( part.substring(1).toLowerCase() );                
			else
				sb.append(part.toUpperCase());

			sb.append(" ");
		}

		return sb.toString().trim();
	}



	public List<String> convertWebElementToTrimmedStringList(List<WebElement> list){

		List<String> lst2 = new ArrayList<String>();

		for(WebElement e : list){
			lst2.add(e.getText().trim());
		}	

		return lst2;

	}

	public void pressControlA() {		
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
	}

	public void pressControlLeft(WebElement element) {		
		Actions action = new Actions(driver);
		action.moveToElement(element).keyDown(Keys.CONTROL).click().keyUp(Keys.CONTROL).build().perform();
	}

	public void holdShiftKeyPressed() {		
		Actions action = new Actions(driver);
		action.keyDown(Keys.SHIFT).perform();;
	}

	public void releaseShiftKeyPressed() {		
		Actions action = new Actions(driver);
		action.keyUp(Keys.SHIFT).perform();
	}

	public boolean verifyAccuracyOfValues(int actual,int expected,int percentanage){

		boolean status = false;

		float max =(float) (expected+(expected*percentanage/100));
		float min =(float) (expected-(expected*percentanage/100));
		if(min <= actual && actual <=max)
			status=true;
		return status;

	}

	public void pressAltlLeftWithOffset(WebElement element,int x,int y) {		
		Actions action = new Actions(driver);
		action.moveToElement(element).moveByOffset(x, y).keyDown(Keys.ALT).click().keyUp(Keys.ALT).build().perform();
	}


	public Keys getNumberKeys(int number) {

		Keys numberKey = null ;
		switch(number){

		case 0: numberKey= Keys.NUMPAD0;break;
		case 1: numberKey= Keys.NUMPAD1;break;
		case 2: numberKey= Keys.NUMPAD2;break;
		case 3: numberKey= Keys.NUMPAD3;break;
		case 4: numberKey= Keys.NUMPAD4;break;
		case 5: numberKey= Keys.NUMPAD5;break;
		case 6: numberKey= Keys.NUMPAD6;break;
		case 7: numberKey= Keys.NUMPAD7;break;
		case 8: numberKey= Keys.NUMPAD8;break;
		case 9: numberKey= Keys.NUMPAD9;break;

		}

		return numberKey;


	}

	public Integer convertIntoInt(String value) {

		return Integer.parseInt(value);
	}


	public static WebDriver openNewChromeInstanceWithDisabledWebGL() {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-webgl");		
		options.addArguments("start-maximized", "forced-maximize-mode","no-default-browser-check", "always-authorize-plugins","test-type");	
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation")); 
		ChromeDriver myDriver = new ChromeDriver(options);

		return myDriver;
	}

	public static void closeChromeBrowser(WebDriver driverInstance) {

		driverInstance.close();
		driverInstance.quit();

	}

	public void pressAndholdShiftKey(WebElement element) {		
		Actions action = new Actions(driver);
		action.moveToElement(element).keyDown(Keys.SHIFT).click().build().perform();
	}

	public void pressAndHoldControlKey(WebElement element) {		
		Actions action = new Actions(driver);
		action.moveToElement(element).keyDown(Keys.CONTROL).click().build().perform();
	}

	public void releaseControlKey() {		
		Actions action = new Actions(driver);
		action.keyUp(Keys.CONTROL).perform();
	}

	public void performCNTRLX() throws InterruptedException {

		waitForTimePeriod(1000);
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).sendKeys("x").keyUp(Keys.CONTROL).build().perform();
		waitForTimePeriod(1000);
	}

	public void performCNTRLV() throws InterruptedException {

		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).build().perform();
		waitForTimePeriod(1000);
	}
	public void performCNTRLC() throws InterruptedException {

		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).build().perform();
		waitForTimePeriod(1000);
	}

	public void pressESCKey() throws InterruptedException {

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).perform();
		waitForTimePeriod(1000);
	}

	public Float convertIntoFloat(String value) {

		return Float.parseFloat(value);
	}
	public Double convertIntoDouble(String value) {

		return Double.parseDouble(value);
	}

	public int roundOffValue(float ele) {

		int value=Math.round(ele);
		LOGGER.info(Utilities.getCurrentThreadId() + "Float value "+ele+ " round off to int as "+value);
		return value;


	}

	public  boolean isAttribtuePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null){
				result = true;
			}
		} catch (Exception e) {}

		return result;
	}

	public void scrollUpUsingPageUp(WebElement elementToBeScrolled, WebElement scrollbar) {

		while(!elementToBeScrolled.isDisplayed())
			scrollbar.sendKeys(Keys.PAGE_UP);
	}

	public void scrollDownUsingPageDown(WebElement elementToBeScrolled, WebElement scrollbar) {

		try {
			mouseHover(scrollbar);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!elementToBeScrolled.isDisplayed())
			scrollbar.sendKeys(Keys.PAGE_DOWN);
	}

	public void scrollDownUsingPerfectScrollbar(WebElement elementToBeScrolled, WebElement scrollbar) {
		try {
			mouseHover(scrollbar);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!elementToBeScrolled.isDisplayed())
			scrollbar.sendKeys(Keys.ARROW_DOWN);


	}

	public void scrollUpUsingArrow(WebElement elementToBeScrolled, WebElement scrollbar) {

		try {
			mouseHover(scrollbar);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!elementToBeScrolled.isDisplayed())
			scrollbar.sendKeys(Keys.ARROW_UP);

	}

	public void scrollRightUsingPerfectScrollbar(WebElement elementToBeScrolled, WebElement scrollbar, int interval, int maxWidth) {

		Actions dragger = new Actions(driver);
		int numberOfPixelsToDragTheScrollbarUp = interval;


		if(interval>0)
			for (int j = 0; j <maxWidth; j = j + numberOfPixelsToDragTheScrollbarUp)
			{
				dragger.moveToElement(scrollbar).clickAndHold().moveByOffset(numberOfPixelsToDragTheScrollbarUp,0).release(scrollbar).build().perform();
				if(elementToBeScrolled.isDisplayed())
					break;
			}
		else
			for (int j = maxWidth; j >interval; j = j - numberOfPixelsToDragTheScrollbarUp)
			{
				dragger.moveToElement(scrollbar).clickAndHold().moveByOffset(numberOfPixelsToDragTheScrollbarUp,0).release(scrollbar).build().perform();
				if(elementToBeScrolled.isDisplayed())
					break;
			}

	}

	public void scrollRightUsingPerfectScrollbar(WebElement scrollbar, int x,int y) {

		Actions dragger = new Actions(driver);
		dragger.moveToElement(scrollbar).clickAndHold().moveByOffset(x,y).release(scrollbar).build().perform();
	}

	public void scrollDownUsingPerfectScrollbar(WebElement elementToBeScrolled, WebElement scrollbar, int interval, int maxHeight) {

		Actions dragger = new Actions(driver);
		int numberOfPixelsToDragTheScrollbarUp = interval;
		for (int j = 0; j <maxHeight; j = j + numberOfPixelsToDragTheScrollbarUp)
		{
			try {
				dragger.moveToElement(scrollbar).clickAndHold().perform();
				waitForTimePeriod(200);
				dragger.moveByOffset(0, numberOfPixelsToDragTheScrollbarUp).perform();
				waitForTimePeriod(200);
				dragger.release(scrollbar).build().perform();
				waitForTimePeriod(200);
				if(elementToBeScrolled.isDisplayed())
					break;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		while(!elementToBeScrolled.isDisplayed())
			scrollbar.sendKeys(Keys.PAGE_UP);

	}


}
