package org.example.selenium.pageObjectModel.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.example.selenium.pageObjectModel.base.BasePage;

public class HomePage  extends BasePage {
	public HomePage(WebDriver driver) throws Exception {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".operator")
	WebElement operator;
	

	// Initializing the Page Objects:
//	public HomePage() {
//		PageFactory.initElements(driver, this);
//	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
//	
//	public boolean verifyCorrectUserName(){
//		return userNameLabel.isDisplayed();
//	}
	
	public FogrsPage clickOnOperatorLink() throws Exception {
		waitForLoadingIconDisappearInNewWebPage(driver);
		Actions builder = new Actions(driver);
		builder.moveToElement(operator).perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		operator.click();
		return new FogrsPage(this.driver);
	}

//	public void waitForLoadingIconDisappearInNewWebPage(WebDriver driver) {
//		log.info("Wait for loading icon display in new web page.");
//		final By cssSelector = By.cssSelector(".ivu-spin-fullscreen");
//		Function<WebDriver, Boolean> waitFn = new Function<WebDriver, Boolean>() {
//			public Boolean apply(WebDriver driver) {
//				try {
//					if (driver.findElement(cssSelector).isDisplayed()) {
//						log.info("Loading icon display in new web page.");
//						return false;
//					}
//				} catch (Exception e) {
//					return true;
//				}
//				return true;
//			}
//		};
//		WebDriverWait wait = new WebDriverWait(driver, 120, 2000);
//		wait.withMessage("Loading icon should disappear in new web page in 120s");
//		wait.until(waitFn);
//		log.info("Loading icon disappear in new web page.");
//	}
//	public DealsPage clickOnDealsLink(){
//		dealsLink.click();
//		return new DealsPage();
//	}
//
//	public TasksPage clickOnTasksLink(){
//		tasksLink.click();
//		return new TasksPage();
//	}
//
//	public void clickOnNewContactLink(){
//		Actions action = new Actions(driver);
//		action.moveToElement(contactsLink).build().perform();
//		newContactLink.click();
//		
//	}

}
