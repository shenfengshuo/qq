package org.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SeleniumUtil {
	public static WebDriver driver = null;

	private static int defaultImplicitlyWaitTimeSecond = 1;

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(3);
			long result = 0;
			switch (number) {
			case 0:
				result = Math.round(Math.random() * 25 + 65);
				sb.append(String.valueOf((char) result));
				break;
			case 1:
				result = Math.round(Math.random() * 25 + 97);
				sb.append(String.valueOf((char) result));
				break;
			case 2:
				sb.append(String.valueOf(new Random().nextInt(10)));
				break;
			}
		}
		return sb.toString();
	}
	
//	public static void input(String locator, String value) {
//		WebElement m1 = find(locator);
//		m1.clear();
//		if (!value.isEmpty())
//			m1.sendKeys(value);
//		m1.click();
//	}

//	public static String text(String locator) {
//		String data = null;
//		try {
//			WebElement w = find(locator);
//			if (w != null)
//				data = w.getText();
//		} catch (Exception e) {
//		}
//		return data;
//	}

	public static WebElement findElement(String locator) {
		By b = null;
		if (locator.indexOf("::") == -1) {
			b = By.xpath(locator.trim());
		} else {
			String[] arr = locator.replace("::", " :: ").split("::");
			locator = arr[arr.length - 1].trim();
			String locby = arr[arr.length - 2].trim();
			if (locby.equalsIgnoreCase("id")) {
				b = By.id(locator);
			} else if (locby.toLowerCase().indexOf("css") > -1) {
				b = By.cssSelector(locator);
			} else if (locby.equalsIgnoreCase("linkText")) {
				b = By.linkText(locator);
			} else if (locby.equalsIgnoreCase("className")) {
				b = By.className(locator);
			} else if (locby.equalsIgnoreCase("name")) {
				b = By.name(locator);
			} else if (locby.equalsIgnoreCase("tagName")) {
				b = By.tagName(locator);
			} else if (locby.equalsIgnoreCase("partialLinkText")) {
				b = By.partialLinkText(locator);
			} else if (locby.equalsIgnoreCase("xpath")) {
				b = By.xpath(locator);
			}
		}
		return driver.findElement(b);
	}

//	public static WebElement find(final String locator) {
//		return new WebDriverWait(driver, 2).until(new ExpectedCondition<WebElement>() {
//			public WebElement apply(WebDriver d) {
//				return findElement(locator);
//			}
//		});
//	}

	public static WebElement findWithThreadWait(String locator, int timesecond) {
		WebElement w = null;
		try {
			Thread.sleep(timesecond * 1000);
			w = findElement(locator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return w;
	}

//	public static void click(String locator) {
//		WebElement w = null;
//		try {
//			w = find(locator);
//		} catch (Exception e) {
//			w = findWithThreadWait(locator, 2);
//		}
//
//		try {
//			w.click();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static void clear(String locator) {
//		WebElement w = null;
//		try {
//			w = find(locator);
//		} catch (Exception e) {
//			w = findWithThreadWait(locator, 2);
//		}
//
//		try {
//			w.clear();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static Select selectByValue(String locator, String value) {
//		Select se = null;
//		try {
//			Thread.sleep(500);
//			se = new Select(find(locator));
//			se.selectByValue(value);
//		} catch (Exception e0) {
//			try {
//				se = new Select(findWithThreadWait(locator, 2));
//				se.selectByValue(value);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		}
//		return se;
//	}

//	public static Select selectByVisibleText(String locator, String value) {
//		Select se = null;
//		try {
//			Thread.sleep(500);
//			se = new Select(find(locator));
//			se.selectByVisibleText(value);
//		} catch (Exception e0) {
//			try {
//				se = new Select(findWithThreadWait(locator, 2));
//				se.selectByVisibleText(value);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		}
//		return se;
//	}

//	public static void selectByIndex(String locator, int index) {
//		Select se = null;
//		try {
//			Thread.sleep(500);
//			se = new Select(find(locator));
//			se.selectByIndex(index);
//		} catch (Exception e0) {
//			try {
//				se = new Select(findWithThreadWait(locator, 2));
//				se.selectByIndex(index);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		}
//	}

	public static void toFirstWindow() {
		String firstWindow = driver.getWindowHandles().iterator().next();
		if (firstWindow != null)
			driver.switchTo().window(firstWindow);
	}

	public static void toLastWindow() {
		String lastWindow = null;
		for (Iterator<String> it = driver.getWindowHandles().iterator(); it.hasNext();)
			lastWindow = it.next();
		if (lastWindow != null)
			driver.switchTo().window(lastWindow);
	}

	public static WebDriver toWindow(String title) {
		title = title.trim();
		for (String s : new ArrayList<String>(driver.getWindowHandles())) {
			driver.switchTo().window(s);
			if (driver.getTitle().trim().equalsIgnoreCase(title))
				break;
		}
		return driver;
	}

	public static void clickAlert(boolean isAccept) {
		try {
			Alert alert = driver.switchTo().alert();
			if (isAccept)
				alert.accept();
			else
				alert.accept();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static WebDriver getIEDriver() {
//		String ieDriverServerPath = Config.resource + "IEDriverServer.exe";
//		System.setProperty("webdriver.ie.driver", ieDriverServerPath);
//		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
//		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//		driver = new InternetExplorerDriver(ieCapabilities);
//		driver.manage().timeouts().implicitlyWait(defaultImplicitlyWaitTimeSecond, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
//		return driver;
//	}
	// 单独重构成一个方法，然后调用
	public static DesiredCapabilities setDownloadsPath(String downloadsPath) {

		File fileDst = new File(downloadsPath);
		if (!fileDst.isDirectory()) {
			fileDst.mkdirs();
		}

//	    String downloadsPath = "D:\\dataSource\\outputReport\\Downloads";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadsPath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(ChromeOptions.CAPABILITY, options);
		return caps;
	}

	public static WebDriver getChromeDriver(String downloadsPath) {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		DesiredCapabilities caps = setDownloadsPath(downloadsPath);// 更改默认下载路径
		driver = new ChromeDriver(caps);
		driver.manage().timeouts().implicitlyWait(defaultImplicitlyWaitTimeSecond, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
//		DesiredCapabilities caps = setDownloadsPath();//更改默认下载路径
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(defaultImplicitlyWaitTimeSecond, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	// public static String snap() {
	// String snapfilepath = Config.picdir;
	// String destinationFileName = MyFile.genName(snapfilepath, ".jpg");
	// File sourceFile = ((TakesScreenshot)
	// driver).getScreenshotAs(OutputType.FILE);
	// try {
	// Files.copy(sourceFile, new File(destinationFileName.toString()));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return destinationFileName;
	// }
	//
	// public static String ps() {
	// String snapfilepath = Config.picdir;
	// String destinationFileName = MyFile.genName(snapfilepath, ".jpg");
	// Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	// try {
	// ImageIO.write(
	// (new Robot()).createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(),
	// (int) d.getHeight())),
	// "jpg", new File(destinationFileName));
	// } catch (Exception ex) {
	// System.out.println(ex);
	// }
	// return destinationFileName;
	// }

	public static void js(String... args) {
		if (args == null || args.length == 0) {
		} else if (args.length == 1) {
			String cmd = args[0];
			((JavascriptExecutor) driver).executeScript(cmd);
		} else if (args.length > 1) {
			String key = args[0];
			String value = args[1];
			String cmd = "document.getElementById(\"" + key + "\").value=\"" + value + "\"";
			((JavascriptExecutor) driver).executeScript(cmd);
		}
	}

	public static void quit() {
//		driver.quit();
		try {
			Runtime.getRuntime().exec("taskkill /F /im " + "chromedriver.exe");
//			Runtime.getRuntime().exec("taskkill /F /im " + "chrome.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	public static void login(String username, String password) {
//		if (driver.getTitle().equals("易付宝")) {
//			WebElement element = find("xpath::.//*[@id='J_StaticForm']/div/div[3]");
//			input("id::username", username);
//			input("id::password", password);
//			Actions actions = new Actions(driver);
//			Random random = new Random();
//			int y = 0;
//			int x = 280;
//			for (int i = 0; i < 5; i++) {
//				Action action = actions.moveToElement(element).clickAndHold().moveByOffset(random.nextInt(279) + 1, y)
//						.build();
//				action.perform();
//			}
//
//			for (int i = 0; i < 10; i++) {
//				Action action = actions.moveToElement(element).clickAndHold().moveByOffset(x, y).build();
//				action.perform();
//			}
//
//			Action action = actions.release(element).build();
//			action.perform();
//			click(".//*[@id='submit']");
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			toLastWindow();
//		}
//
//	}
//	public static void main(String arg[]){
//		getChromeDriver();
//		
//	}
}
