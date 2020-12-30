package com.test.MoeInnovationCell;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.page.factory.MoeInnovationCell;
import com.page.factory.MoeInnovationConstant;

public class MoeInnovationTest {

	public WebDriver driver;
	MoeInnovationCell obj;

	@BeforeMethod
	public void BrowserRun() throws FileNotFoundException {


		DesiredCapabilities cap= new DesiredCapabilities();
		cap.setAcceptInsecureCerts(true);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		System.setProperty("webdriver.chrome.driver", "E:\\InnovationCell\\Drivers\\chromedriver.exe");

		ChromeOptions option=new ChromeOptions();
		option.addArguments("--dont-require-litepage-redirect-infobar");
		option.setAcceptInsecureCerts(true);
		option.setExperimentalOption("useAutomationExtension", false);
		option.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		option.addArguments("--start-maximized");
		option.addArguments("--incognito");
		option.addArguments("--disable-infobar");

		option.merge(cap);

		driver = new ChromeDriver(option);

		obj = new MoeInnovationCell(driver);
		obj.navigateToURL("https://iic.mic.gov.in/");

		obj.waitForElementVisibility(obj.mheInnovationLogo);




	}

	// In this method Tried to verify presence of MoeLogo

	@Test
	public void verifyPresenceOfMoeLogo()
	{

		obj = new MoeInnovationCell(driver);

		obj.assertTrue(obj.isElementPresent(obj.mheInnovationLogo), "ChecPoint[1/10]-Verifying logo is present or not", "Veirfying Presence of Innovation Logo on Main page.");
	}



	// In this method , tried to verify presence of Header and Contents



	@Test
	public void verifyPresenceOfHeaderAndContents() throws InterruptedException
	{

		obj = new MoeInnovationCell(driver);

		//Verifying presence of Header
		obj.assertTrue(obj.isElementPresent(obj.headerContainer), "ChecPoint[2/10]-Verifying header is present or not", "Veirfying Presence of header on Main page.");

		// Verifying links present or not header.

		for(int i=0; i<obj.headerLinks.size();i++)
			obj.assertTrue(obj.isElementPresent(obj.headerLinks.get(i)), "Veirying presence of Header links", "Headers links are present.");

		// Mouse hover on every header links.
		for(int i=0; i<obj.headerLinks.size();i++) {
			//obj.mouseHover(obj.headerLinks.get(i));
			System.out.println("Path is:---"+obj.headerLinks.get(i));

			obj.hoverMouseOnHeaderLinks(obj.headerLinks.get(i));
		}
	}


	// In this method tried to click on and Login button and verified login page.
	@Test
	public void clickOnLoginButtonAndVerifyLoginPage() throws InterruptedException


	{

		obj = new MoeInnovationCell(driver);

		String url=obj.getCurrentPageURL();
		obj.click(obj.login);
		obj.switchToNewWindow(2);
		obj.waitForElementVisibility(obj.InstitueLoginText);

		obj.assertNotEquals(url, obj.getCurrentPageURL(), "CheckPoint[1/7]", "Verifying URL is not same hence Loginpage is loaded");

		obj.assertTrue(obj.isElementPresent(obj.emailField), "CheckPoint[2/7]", "Verifying present of "+obj.emailField+" field on 'Institute Login' page");
		obj.assertTrue(obj.isElementPresent(obj.passwordField), "CheckPoint[3/7]", "Verifying present of "+obj.passwordField+" field on 'Institute Login' page");
		obj.assertTrue(obj.isElementPresent(obj.guideAndPortalManual), "CheckPoint[4/7]", "Verifying present of "+obj.guideAndPortalManual+" field on 'Institute Login' page");
		obj.assertTrue(obj.isElementPresent(obj.forgotPassword), "CheckPoint[5/7]", "Verifying present of "+obj.forgotPassword+" field on 'Institute Login' page");
		obj.assertTrue(obj.isElementPresent(obj.loginButton), "CheckPoint[6/7]", "Verifying present of "+obj.loginButton+" field on 'Institute Login' page");
		obj.assertTrue(obj.isElementPresent(obj.forgotPassword), "CheckPoint[7/7]", "Verifying present of "+obj.forgotPassword+" field on 'Institute Login' page");
	}


	//In this method tried to click on SignUp button and verified the signup page.
	@Test
	public void clickOnSingUpButtonAndVerifySignUpPage() throws InterruptedException


	{

		obj = new MoeInnovationCell(driver);

		String url=obj.getCurrentPageURL();
		obj.click(obj.singUpButton);
		obj.switchToNewWindow(2);
		obj.waitForElementVisibility(obj.singUpText);

		obj.assertTrue(obj.isElementPresent(obj.singUpText), "CheckPoint[1/2]", "Verifying present of "+obj.singUpButton+" field on 'Singup' page");
		obj.assertNotEquals(url, obj.getCurrentPageURL(), "CheckPoint[2/2]", "Verifying URL is not same hence SignUp is loaded");
	}

	// In this method tried to verify the presence of Map and all states

	@Test
	public void verifyMapIsPresentAndAllRegions() throws InterruptedException
	{
		obj = new MoeInnovationCell(driver);
		obj.scrollIntoView(obj.mapIndiaSvg);
		obj.waitForAllChangesToLoad();
		obj.scrollToEnd();
		obj.assertTrue(obj.isElementPresent(obj.mapIndiaSvg), "CheckPoint[1/2]", "Verifying present of "+obj.mapIndiaSvg+" field on 'MOEInnovation' page");

		for(int i=0; i<obj.regions.size();i++)
		{
			obj.mouseHover(obj.regions.get(i));
			obj.waitForAllChangesToLoad();
		}
	}


	// Verify the presence of footer and links

	@Test
	public void verifyFooterLink() throws InterruptedException


	{

		obj = new MoeInnovationCell(driver);
		obj.scrollIntoView(obj.footer);
		obj.waitForAllChangesToLoad();

		obj.assertTrue(obj.isElementPresent(obj.quickLinkText), "CheckPoint[2/7]", "Verifying present of "+obj.quickLinkText+" field on 'footer of the MOE Innovation' page");
		obj.assertTrue(obj.isElementPresent(obj.RelatedLinkText), "CheckPoint[3/7]", "Verifying present of "+obj.RelatedLinkText+" field on 'footer of the MOE Innovation' page");
		obj.assertTrue(obj.isElementPresent(obj.socialMediaLinkText), "CheckPoint[4/7]", "Verifying present of "+obj.socialMediaLinkText+" field on 'footer of the MOE Innovation' page");
		obj.assertTrue(obj.isElementPresent(obj.contactLinkText), "CheckPoint[5/7]", "Verifying present of "+obj.contactLinkText+" field on 'footer of the MOE Innovation' page");
		obj.assertTrue(obj.isElementPresent(obj.copyRightText), "CheckPoint[6/7]", "Verifying present of "+obj.copyRightText+" field on 'footer of the MOE Innovation' page");
		obj.assertTrue(obj.isElementPresent(obj.allRightReservedText), "CheckPoint[7/7]", "Verifying present of "+obj.allRightReservedText+" field on 'footer of the MOE Innovation' page");

		for(int i=0; i<obj.quickLinksOption.size();i++)
		{
			obj.mouseHover(obj.quickLinksOption.get(i));
			obj.waitForAllChangesToLoad();
		}


		for(int i=0; i<obj.relatedLinksOption.size();i++)
		{
			obj.mouseHover(obj.relatedLinksOption.get(i));
			obj.waitForAllChangesToLoad();
		}

		for(int i=0; i<obj.socialMediaLinksOption.size();i++)
		{
			obj.mouseHover(obj.socialMediaLinksOption.get(i));
			obj.waitForAllChangesToLoad();
		}


		for(int i=0; i<obj.contactLinksOption.size();i++)
		{
			obj.mouseHover(obj.contactLinksOption.get(i));
			obj.waitForAllChangesToLoad();
		}


		obj.mouseHover(obj.copyRightText);
		obj.mouseHover(obj.copyRightText);


	}



	// In this method tried to verify the Arrow button functionality and image changing on clicking on button
	@Test
	public void verifyArrowButtonWorking() throws InterruptedException


	{

		obj = new MoeInnovationCell(driver);
		obj.waitForTimePeriod(5000);

		while(!(obj.isElementPresent(obj.textOnNextSlide)))
		{


			obj.click(obj.forwardArrow);
			obj.waitForTimePeriod(5000);
			obj.assertTrue(obj.getText(obj.textOnNextSlide).equalsIgnoreCase(MoeInnovationConstant.SLIDE_CONTEXT), "CheckPoint[1/2]", "Verifying content present on next slide is matching");

		}

		obj.click(obj.forwardArrow);
		obj.waitForTimePeriod(5000);
		obj.assertFalse(obj.getText(obj.textOnNextSlide).equalsIgnoreCase(MoeInnovationConstant.SLIDE_CONTEXT), "CheckPoint[1/2]", "Verifying content present on next slide is matching or not");

		obj.click(obj.backwardArrow);
		obj.waitForTimePeriod(5000);
		obj.assertTrue(obj.getText(obj.textOnNextSlide).equalsIgnoreCase(MoeInnovationConstant.SLIDE_CONTEXT), "CheckPoint[1/2]", "Verifying content present on next slide is matching");


	}

	@AfterMethod
	public void After() {


		driver.close();
		driver.quit();
	}


}


