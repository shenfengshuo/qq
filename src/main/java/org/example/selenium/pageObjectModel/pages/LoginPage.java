package org.example.selenium.pageObjectModel.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.example.selenium.pageObjectModel.base.BasePage;
//import selenium.fogrsPageObjectModel.base.TestBase;

public class LoginPage extends BasePage {
	public LoginPage(WebDriver driver) throws Exception {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	//Page Factory - OR:
	@FindBy(name="username")
	WebElement username;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(linkText="登录")
	WebElement loginBtn;
	

	
	//Initializing the Page Objects:
//	public LoginPage() throws Exception{
//		super(driver);
//		PageFactory.initElements(driver, this);
//	}
	
	//Actions:
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}
	
//	public boolean validateCRMImage(){
//		return crmLogo.isDisplayed();
//	}
	
	public HomePage login(String name, String pwd) throws Exception{
		username.sendKeys(name);
		password.sendKeys(pwd);
		//loginBtn.click();
		    	JavascriptExecutor js = (JavascriptExecutor)driver;
		    	js.executeScript("arguments[0].click();", loginBtn);

		return new HomePage(this.driver);
	}
	
}
