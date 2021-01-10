package org.example.selenium.pageObjectModel.base;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;

//import com.shen.CommonTool.TakeScreenShootListener;

//@Listeners({ TakeScreenShootListener.class })
//
public class BasePage {
	public static WebDriver driver;
	// protected String[][] locatorMap;
	HashMap<String, Locator> locatorMap;
	String path;
//	protected Log log = new Log(this.getClass());
//	private static Log logger = new Log(BasePage.class);

	public BasePage(WebDriver driver) throws Exception {
		this.driver = driver;

		// log.debug(this.getClass().getCanonicalName());

		System.out.println("当前类" + this.getClass().getName());
		String path1 = this.getClass().getSimpleName() + ".xml";
		File dir = new File("src/main/resources/testfolder");
		path = dir.getAbsolutePath() + "/" + this.getClass().getSimpleName() + ".xml";
//		locatorMap = ElementXml.readXMLDocument(path, this.getClass().getCanonicalName());

	}

	@BeforeClass
	public void initsth() {
//		LogConfigution.initLog(this.getClass().getCanonicalName());

	}

	protected void type(Locator locator, String values) throws Exception {
		WebElement e = findElement(driver, locator);
//		logger.info("type value is:  " + values);
		e.sendKeys(values);
	}

	public void click(Locator locator) throws Exception {
		WebElement e = findElement(driver, locator);
//		logger.info("click button");
		e.click();
	}

	public String getText(Locator locator) throws Exception {
		WebElement e = findElement(driver, locator);
//		logger.info("click button");
		return e.getText();
	}

	protected void switchFrame(Locator locator) throws Exception {
		driver.switchTo().frame(locator.getElement());
//		logger.info("swith frame");

	}

	protected void select(Locator locator, String value) throws Exception {
		WebElement e = findElement(driver, locator);
		Select select = new Select(e);

		try {

//			logger.info("select by Value " + value);
			select.selectByValue(value);
		} catch (Exception notByValue) {
//			logger.info("select by VisibleText " + value);
			select.selectByVisibleText(value);
		}
	}

	protected void alertConfirm() {
		Alert alert = driver.switchTo().alert();
		try {
			alert.accept();
		} catch (Exception notFindAlert) {
			throw notFindAlert;
		}
	}

	protected void alertDismiss() {
		Alert alert = driver.switchTo().alert();
		try {
			alert.dismiss();
		} catch (Exception notFindAlert) {
			throw notFindAlert;
		}
	}

	protected String getAlertText() {
		Alert alert = driver.switchTo().alert();
		try {
			return alert.getText();
		} catch (Exception notFindAlert) {
			throw notFindAlert;
		}
	}

	protected void clickAndHold(Locator locator) throws IOException {
		WebElement e = findElement(driver, locator);
		Actions actions = new Actions(driver);
		actions.clickAndHold(e).perform();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getElement(Locator locator) throws IOException {
		return getElement(this.getDriver(), locator);
	}

	/**
	 * get by parameter
	 * 
	 * @author Young
	 * @param driver
	 * @param locator
	 * @return
	 * @throws IOException
	 */
	public WebElement getElement(WebDriver driver, Locator locator) throws IOException {
		locator = getLocator(locator.getElement());
		WebElement e;
		switch (locator.getBy()) {
		case xpath:
//			logger.debug("find element By xpath");
			e = driver.findElement(By.xpath(locator.getElement()));
			break;
		case id:
//			logger.debug("find element By id");
			e = driver.findElement(By.id(locator.getElement()));
			break;
		case name:
//			logger.debug("find element By name");
			e = driver.findElement(By.name(locator.getElement()));
			break;
		case cssSelector:
//			logger.debug("find element By cssSelector");
			e = driver.findElement(By.cssSelector(locator.getElement()));
			break;
		case className:
//			logger.debug("find element By className");
			e = driver.findElement(By.className(locator.getElement()));
			break;
		case tagName:
//			logger.debug("find element By tagName");
			e = driver.findElement(By.tagName(locator.getElement()));
			break;
		case linkText:
//			logger.debug("find element By linkText");
			e = driver.findElement(By.linkText(locator.getElement()));
			break;
		case partialLinkText:
//			logger.debug("find element By partialLinkText");
			e = driver.findElement(By.partialLinkText(locator.getElement()));
			break;
		default:
			e = driver.findElement(By.id(locator.getElement()));
		}
		return e;
	}

	public boolean isElementPresent(WebDriver driver, Locator myLocator, int timeOut) throws IOException {
		final Locator locator = getLocator(myLocator.getElement());
		boolean isPresent = false;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		isPresent = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return findElement(d, locator);
			}
		}).isDisplayed();
		return isPresent;
	}

	/**
	 * This Method for check isPresent Locator
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 * @throws IOException
	 */
	public boolean isElementPresent(Locator locator, int timeOut) throws IOException {
		return isElementPresent(driver, locator, timeOut);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public WebElement findElement(WebDriver driver, final Locator locator) {
		WebElement element = (new WebDriverWait(driver, locator.getWaitSec()))
				.until(new ExpectedCondition<WebElement>() {

					@Override
					public WebElement apply(WebDriver driver) {
						try {
							return getElement(driver, locator);
						} catch (IOException e) {
							// TODO Auto-generated catch block
//							logger.error("can't find element " + locator.getElement());
							return null;
						}

					}

				});
		return element;

	}

	/**
	 * @author Young
	 * 
	 * @param locatorName
	 * @return
	 * @throws IOException
	 */
	public Locator getLocator(String locatorName) throws IOException {

		Locator locator;
		// for (int i = 0; i < locatorMap.length; i++) {
		// if (locatorMap[i][0].endsWith(locatorName)) {
		// return locator = new Locator(locatorMap[i][1]);
		// }
		// }
		// return locator = new Locator(locatorName);
		locator = locatorMap.get(locatorName);
		if (locator == null) {
			locator = new Locator(locatorName);
		}
		return locator;

	}
	
	public void waitForLoadingIconDisappearInNewWebPage(WebDriver driver) {
//		log.info("Wait for loading icon display in new web page.");
		Function<WebDriver, Boolean> waitFn = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					if (driver.findElement(By.cssSelector(".ivu-spin")).isDisplayed()
							|| driver.findElement(By.cssSelector(".ivu-spin-fullscreen")).isDisplayed()) {
//						log.info("Loading icon display in new web page.");
						return false;
					}
				} catch (Exception e) {
					return true;
				}
				return true;
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 120, 1000);
		wait.withMessage("Loading icon should disappear in new web page in 120s");
		wait.until(waitFn);
//		log.info("Loading icon disappear in new web page.");
	}
}