package com.page.factory;



import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;


import com.web.page.WebAction;




public class MoeInnovationCell extends WebAction {


	//private WebDriver driver;

	public MoeInnovationCell(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);	
	}


	// Logo 

	public By patientIDOnViewer1 = By.className("vc_single_image-img topLogo topLogoLandingPage");	

	@FindBy(xpath="//div[@class='wpb_wrapper']//*[contains(@class,'topLogo')]")
	public WebElement mheInnovationLogo; 

	// MoeHeaderMenu Links

	@FindBy(css=".width-auto.wpb_column.vc_column_container.vc_col-sm-8")
	public WebElement headerContainer;


	@FindBys(@FindBy(xpath = "//div[@id='cbp-menu-main']//ul[@class='sf-menu wh-menu-main']/li"))
	public List<WebElement> headerLinks;


	@FindBy(xpath="//img[@class='mic-logo']")
	public WebElement micLogo;


	@FindBy(css=".breadcrumb-item a")
	public WebElement breadCrumbHomeLink;


	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main']//a[text()='Home' and @href='/']")
	public WebElement home; 

	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main']//a[text()='About Us ' and @href='javascript: void(0)']")
	public WebElement aboutUS; 

	@FindBys(@FindBy(xpath = "//ul[@class='sf-menu wh-menu-main']//a[text()='About Us ' and @href='javascript: void(0)']/i//parent::a//following-sibling::ul/li"))
	public List<WebElement> aboutUsOption;


	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main']//a[text()='IIC 2020-21' and @href='/iic202021']")
	public WebElement iIC; 


	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main']//a[text()='At a Glance ' and @href='javascript: void(0)']")
	public WebElement atAGlance; 


	@FindBys(@FindBy(xpath = "//ul[@class='sf-menu wh-menu-main']//a[text()='At a Glance ' and @href='javascript: void(0)']/i//parent::a//following-sibling::ul/li"))
	public List<WebElement> atAGlanceOption;


	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main']//a[text()='Major Activities ' and @href='javascript: void(0)']")
	public WebElement majorActivities; 


	@FindBys(@FindBy(xpath = "//ul[@class='sf-menu wh-menu-main']//a[text()='Major Activities ' and @href='javascript: void(0)']/i//parent::a//following-sibling::ul/li"))
	public List<WebElement> majorActivitiesOption;


	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main']//a[text()='Resources & Referrals ' and @href='javascript: void(0)']")
	public WebElement resourcesReferral; 


	@FindBys(@FindBy(xpath = "//ul[@class='sf-menu wh-menu-main']//a[text()='Resources & Referrals ' and @href='javascript: void(0)']/i//parent::a//following-sibling::ul/li"))
	public List<WebElement> resourcesReferralOption;


	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main']//a[text()='Notification' and @href='/iic-notification']")
	public WebElement notificationLink; 


	//Login Page


	@FindBy(xpath="//*[contains(text(),'Institute Login')]")
	public WebElement InstitueLoginText; 

	@FindBy(xpath="//button[@type='submit']")
	public WebElement loginButton; 

	@FindBy(xpath="//*[contains(text(),'New Registration')]")
	public WebElement newRegistration; 

	@FindBy(xpath="//*[contains(text(),'Forgot Password')]")
	public WebElement  forgotPassword; 

	@FindBy(css="input#email")
	public WebElement emailField;

	@FindBy(css="input#password")
	public WebElement passwordField;

	@FindBy(xpath="//*[@id='recaptcha-anchor']")
	public WebElement captchaCheckBox;

	@FindBy(xpath="//*[contains(text(),'IIC Guide and Portal Manual')]")
	public WebElement guideAndPortalManual; 




	//SignUp Page


	@FindBy(xpath="//*[contains(text(),'Sign Up')]")
	public WebElement singUpText; 

	//------------------------------------

	// Login and Singup	
	@FindBys(@FindBy(xpath = "sf-menu wh-menu-main sf-menu wh-menu-main-login"))
	public List<WebElement> loginAndSignUp;

	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main sf-menu wh-menu-main-login']//a[@href='/login']")
	public WebElement login; 

	@FindBy(xpath="//ul[@class='sf-menu wh-menu-main sf-menu wh-menu-main-login']//a[@href='/signup']")
	public WebElement singUpButton; 

	//------------------------------------



	//Footer Links


	@FindBy(css = "div.cbp-container.wh-padding.footerMainBottomBox")
	public WebElement footer;

	@FindBys(@FindBy(xpath = "//div[@class='vc_column-inner']/div/div"))
	public List<WebElement> quickLinksOption;

	@FindBy(xpath ="//h5[(text()='Quick Links')]")
	public WebElement quickLinkText;

	@FindBys(@FindBy(xpath = "//div[@class='vc_column-inner vc_custom_1490007556855']/div/div"))
	public List<WebElement> relatedLinksOption;

	@FindBy(xpath ="//h5[(text()='Related Links')]")
	public WebElement RelatedLinkText;

	@FindBys(@FindBy(xpath ="//div[@class='vc_column-inner vc_custom_1490007547463']/div/div"))
	public List<WebElement> socialMediaLinksOption;

	@FindBy(xpath ="//h5[(text()='Social Media Links')]")
	public WebElement socialMediaLinkText;

	@FindBy(xpath ="//h5[(text()='Contact')]")
	public WebElement contactLinkText;

	@FindBys(@FindBy(xpath ="//div[@class='vc_column-inner vc_custom_1498816102915']/div/p/a"))
	public List<WebElement> contactLinksOption;

	@FindBy(xpath ="//*[contains(text(),'Copyright IIC © 2020')]")
	public WebElement copyRightText;

	@FindBy(xpath ="//*[contains(text(),'All Rights Reserved')]")
	public WebElement allRightReservedText;


	// Map


	@FindBys(@FindBy(css = "#chart g:nth-child(1) path"))
	public List<WebElement> regions;



	@FindBy(css="#chart g:nth-child(1) ")
	public WebElement mapIndiaSvg;



	//Navigation Arrow-------------------------

	@FindBy(css="a.carousel-control-prev.landingSliderBannerPrev i.fa.fa-arrow-left")
	public WebElement backwardArrow;

	@FindBy(css="a.carousel-control-next.landingSliderBannerNext i.fa.fa-arrow-right")
	public WebElement forwardArrow;

	@FindBy(xpath="//p[@class='ls-l'][contains(text(),'Provides National Platform for')]")
	public WebElement  textOnNextSlide;


	public void waitForAllChangesToLoad(){
		waitForEndOfAllAjaxes();
	}


	public void scrollDown(int scrollCount, int delay) throws InterruptedException {

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		waitForTimePeriod(delay);
		scrollCount--;
		if(scrollCount > 0) {
			scrollDown(scrollCount, delay);
		}
	}

	public void scrollDownToSliceUsingKeyboard(int scrollAmount) throws InterruptedException{


		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_DOWN,Keys.RETURN).build().perform();
		waitForTimePeriod(500);
		if (scrollAmount==1)
			return;
		else
			scrollDownToSliceUsingKeyboard(scrollAmount-1);

	}



	public void scrollUpToSliceUsingKeyboard(int scrollAmount) throws InterruptedException{


		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_UP).build().perform();
		waitForTimePeriod(500);
		if (scrollAmount==1)
			return;
		else
			scrollUpToSliceUsingKeyboard(scrollAmount-1);

	} 


	public void scrollUpSlice(int scrollAmount, int delay) throws InterruptedException{


		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_UP).build().perform();
		waitForTimePeriod(delay);
		if (scrollAmount==1)
			return;
		else
			scrollUpToSliceUsingKeyboard(scrollAmount-1);

	} 

	public void hoverMouseOnHeaderLinks(WebElement element) throws InterruptedException
	{

		mouseHover(element);
		if (element.getAttribute("class").contains("childern")) {
			String name=getText(getElement(By.xpath("//div[@id='cbp-menu-main']//ul[@class='sf-menu wh-menu-main']/li/a")));
			click("//ul[@class='sf-menu wh-menu-main']//a[text()='"+name+"' and @href='javascript: void(0)']");



			List<WebElement> elements=driver.findElements(By.xpath("//div[@id='cbp-menu-main']//ul[@class='sf-menu wh-menu-main']/li/a/i/parent::a/following-sibling::ul"));

			for(int i=0; i<elements.size();i++) {
				WebElement listElement =elements.get(i);
				mouseHover(elements.get(i));
			}

		}

		else
			mouseHover(element);


	}


}





