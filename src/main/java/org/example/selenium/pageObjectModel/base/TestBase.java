package org.example.selenium.pageObjectModel.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.utils.TestUtil;
import org.example.utils.WebEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class TestBase {
//public static final Logger log = LoggerFactory.getLogger(TestBase.class);
	WebDriver driver;
	ChromeDriverService service;
	String OS = System.getProperty("os.name").toLowerCase();

	public String getOS() {
		return OS;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}
	
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;

	public void getBase() {
		System.out.println("Operator System  " + OS);
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("src/main/resources/config.properties");
//FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/crm"
//+ "/qa/config/config.properties");
			prop.load(ip);

//			driver = new ChromeDriver();
			
			if (OS.contains("win")) {
				String path = "src/main/resources/chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", path);
				service = new ChromeDriverService.Builder().usingDriverExecutable(new File(path)).usingAnyFreePort()
						.build();
				service.start();
				// ChromeDriver是轻量级的服务，在单任务或不需要频繁启动浏览器的情况下，使用driver.quit()关闭浏
				// 览器，可以正常结束ChromeDriver进程。若在一个比较大的 测试套件中频繁的启动关闭，会增加一个比较明显的延时导致浏览器
				// 进程不被关闭的情况发生，为了避免这一状况我们可以通过ChromeDriverService来控制ChromeDriver进程的生死，达
				// 到用完就关闭的效果避免进程占用情况出现（Running the server in a child process）
				ChromeOptions chromeOptions = new ChromeOptions();
				// chromeOptions.addArguments("headless"); //无头模式
				// driver = new ChromeDriver(chromeOptions); //无头模式
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			} else {
				String path = "/usr/bin/phantomjs";
				System.setProperty("phantomjs.binary.path", path);

				driver = new PhantomJSDriver();
				driver.manage().window().maximize();
			}
			
			this.setDriver(driver);
			driver.manage().deleteAllCookies();
			//driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
//			driver.get(prop.getProperty("xgurl"));
			
			
//			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.get(prop.getProperty("xzurl"));
			// sit
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public static void initialization() {
//		String browserName = prop.getProperty("browser");

//		if (browserName.equals("chrome")) {
//			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
//			driver = new ChromeDriver();
//		} else if (browserName.equals("FF")) {
//			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
//			driver = new FirefoxDriver();
//		}

//e_driver = new EventFiringWebDriver(driver);
//Now create object of EventListerHandler to register it with EventFiringWebDriver
//eventListener = new WebEventListener();
//e_driver.register(eventListener);
//driver = e_driver;

//		driver = new EventFiringWebDriver(driver).register(new WebEventListener());
//
//		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
//driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
//driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
//		driver.get(prop.getProperty("xgurl"));
//
//	}

	public void waitForLoadingIconDisappearInNewWebPage(WebDriver driver) {
//log.info("Wait for loading icon display in new web page.");
		Function<WebDriver, Boolean> waitFn = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					if (driver.findElement(By.cssSelector(".ivu-spin")).isDisplayed()
							|| driver.findElement(By.cssSelector(".ivu-spin-fullscreen")).isDisplayed()) {
//log.info("Loading icon display in new web page.");
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
//log.info("Loading icon disappear in new web page.");
	}

}
