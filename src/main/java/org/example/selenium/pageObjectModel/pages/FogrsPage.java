package org.example.selenium.pageObjectModel.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.example.selenium.pageObjectModel.base.BasePage;
//import selenium.fogrsPageObjectModel.pages.sysManager.RolelistPage;
//import selenium.fogrsPageObjectModel.pages.sysManager.UserlistPage;

public class FogrsPage  extends BasePage{
	public FogrsPage(WebDriver driver) throws Exception {
		super(driver);
		PageFactory.initElements(driver, this);

	}
	//系统设置
	@FindBy(css = "div:nth-child(2) > .ivu-menu-submenu > .ivu-menu-submenu-title")
	WebElement sysMgr;
	//用户管理
	@FindBy(css = ".ivu-menu-opened .ivu-menu-item:nth-child(1)")
	WebElement userlist;
	//角色管理
	@FindBy(css = ".ivu-menu-item-active .ivu-menu-item:nth-child(2)")
	WebElement rolelist;
	
	//对账业务配置列表
	@FindBy(css = "div:nth-child(3) .ivu-menu-submenu-title")
	WebElement businessLable;
	//对账业务配置
	@FindBy(css = ".ivu-menu-opened .ivu-menu-item:nth-child(1)")
	WebElement business;
	
	//业务数据获取列表
	@FindBy(css = "div:nth-child(4) .ivu-menu-submenu-title")
	WebElement datasourceLabel;
	//数据源管理
	@FindBy(css = ".ivu-menu-opened .ivu-menu-item:nth-child(1)")
	WebElement datasourceManager;
	//数据字段模板
	@FindBy(css = ".ivu-menu-item-active .ivu-menu-item:nth-child(2)")
	WebElement datasourceTemplate;
	

	//业务数据处理列表
	@FindBy(css = "div:nth-child(5) .ivu-menu-submenu-title")
	WebElement taskLabel;
	//对账任务配置
	@FindBy(css = ".ivu-menu-opened .ivu-menu-item:nth-child(1)")
	WebElement taskMag;
	//任务执行记录
	@FindBy(css = ".ivu-menu-item-active .ivu-menu-item:nth-child(2)")
	WebElement tasklog;

	// Initializing the Page Objects:
//	public FogrsPage() {
//		PageFactory.initElements(driver, this);
//	}
	
	
//	
//	public boolean verifyCorrectUserName(){
//		return userNameLabel.isDisplayed();
//	}
	
	//系统设置用户
//	public UserlistPage clickOnUserLink(){
//		sysMgr.click();
//		userlist.click();
//		return new UserlistPage();
//	}
	
	//系统设置角色
//	public RolelistPage clickOnRoleLink(){
//		sysMgr.click();
//		rolelist.click();
//		return new RolelistPage();
//	}
	
	//数据源页面
//	public DatasourceManagerPage clickOnDatasourceLabelLink(){
//		waitForLoadingIconDisappearInNewWebPage(driver);
//		datasourceLabel.click();
//		datasourceManager.click();
//		return new DatasourceManagerPage();
//	}
	
	//任务创建页面
	public TaskPage clickOnTaskLabelLink() throws Exception{
		waitForLoadingIconDisappearInNewWebPage(driver);
		taskLabel.click();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		taskMag.click();
		return new TaskPage(this.driver);
	}
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
