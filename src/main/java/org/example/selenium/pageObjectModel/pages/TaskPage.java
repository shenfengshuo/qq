package org.example.selenium.pageObjectModel.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.example.selenium.pageObjectModel.base.BasePage;

public class TaskPage extends BasePage {
	public TaskPage(WebDriver driver) throws Exception {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	//对账业务配置列表
	@FindBy(css = ".ivu-form-item-content > .ivu-btn:nth-child(2) > span")
	WebElement newBtn;
	

	// Initializing the Page Objects:
//	public TaskPage() {
//		PageFactory.initElements(driver, this);
//	}
	
//	public TaskNewPage1 clickOnnewBtn(){
//		waitForLoadingIconDisappearInNewWebPage(driver);
//		newBtn.click();
//		return new TaskNewPage1();
//	}
	

}
