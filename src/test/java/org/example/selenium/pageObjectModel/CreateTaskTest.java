package org.example.selenium.pageObjectModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.example.selenium.pageObjectModel.base.TestBase;
import org.example.selenium.pageObjectModel.pages.FogrsPage;
import org.example.selenium.pageObjectModel.pages.HomePage;
import org.example.selenium.pageObjectModel.pages.LoginPage;
import org.example.selenium.pageObjectModel.pages.TaskPage;
import org.example.utils.TestUtil;

// TASK20191125871.json组合 hive hive

// TASK201912202319.json非组合 hive hive     ok
// TASK20191127879.json非组合 ftp hive     ok
// TASK201912202305.json非组合 ftp ftp     ok
//TASK2019122323299 纵向合并数据源hive
//纵向合并数据源ftp
//横向合并数据源hive
public class CreateTaskTest {

	String fileName = "t2019122323299.json";
	TestBase testBase;
//	TestUtil testUtil;
	LoginPage loginPage;
	HomePage homePage;
	FogrsPage fogrsPage;
	TaskPage taskPage;
//	public CreateTaskTest() {
//		super();
//	}

	@BeforeMethod
	public void setUp() throws Exception {
		testBase = new TestBase();
		testBase.getBase();
//		testUtil = new TestUtil();
		loginPage = new LoginPage(testBase.getDriver());
		homePage = loginPage.login("188040364", "188040364");
//		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		fogrsPage = homePage.clickOnOperatorLink();
		fogrsPage.clickOnTaskLabelLink();
	}

	@Test(priority = 1)
	public void createTaskTest1() {
		String datasourceA = "DS2020042812416";
		String datasourceB = "DS2020042812419";

		JSONArray taskData = TestUtil.getTaskJSONArray(fileName);

		if (taskData.size() == 1) {
			System.out.println("isNot unit task  非组合任务");
			JSONObject taskJson = JSON.parseObject(taskData.get(0).toString());

			JSONObject taskInfo = JSON.parseObject(taskJson.get("taskInfo").toString());
			JSONArray warnRecipientList = JSON.parseArray(taskJson.get("warnRecipientList").toString());
			JSONObject blendRuleEntity = JSON.parseObject(taskJson.get("blendRuleEntity").toString());

			JSONObject ds0 = JSON.parseObject(taskJson.get("ds0").toString());
			JSONObject ds1 = JSON.parseObject(taskJson.get("ds1").toString());

			JSONObject ds0TemplateInfo = JSON.parseObject(ds0.get("templateInfo").toString());
			JSONObject ds1TemplateInfo = JSON.parseObject(ds1.get("templateInfo").toString());
			JSONArray resultSetList = JSON.parseArray(taskJson.get("resultSetList").toString());

			if (ds0.get("isGroup").equals(0)) {
				// 非组合数据源

			}

		}

	}

//	@Test(priority=2)
//	public void crmLogoImageTest(){
//		boolean flag = loginPage.validateCRMImage();
//		Assert.assertTrue(flag);
//	}

	@AfterMethod
	public void tearDown() {
//		driver.quit();
	}

}
