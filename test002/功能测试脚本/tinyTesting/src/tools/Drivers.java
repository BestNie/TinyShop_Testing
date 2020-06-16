package tools;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Drivers {
	public static final int FF = 0;
	public static final int CH = 1;
	public static final int IE = 2;

	public static WebDriver getDriver(int bt) {
		WebDriver driver = null;
		switch (bt) {
		case IE:
			driver = getIEDriver();
			break;
		case CH:
			driver = getChromeDriver();
			break;
		case FF:
			driver = getFFDriver();
			break;
		}
		driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver getDriver(int bt, int waitsec) {
		WebDriver driver = getDriver(bt);
		driver.manage().timeouts().implicitlyWait(waitsec, TimeUnit.SECONDS);
		return driver;
	}

	public static WebDriver getFFDriverByProfile(String profile) {
		return getFFDriver(profile);
	}

	public static WebDriver getFFDriverByProfile(String profile, int waitsec) {
		WebDriver ffDriver = getFFDriver(profile);
		ffDriver.manage().timeouts().implicitlyWait(waitsec, TimeUnit.SECONDS);
		return ffDriver;
	}

	private static WebDriver getChromeDriver() {
		WebDriver driver = null;
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments("--allow-file-access-from-files", "--disable-infobars", "--disable-prompt-on-repost",
				"--disable-smooth-scrolling", "--disable-popup-blocking");
		driver = new ChromeDriver(options2);
		return driver;
	}

	private static WebDriver getFFDriver() {
		WebDriver driver = null;
		System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
		driver = new FirefoxDriver();
		return driver;
	}

	private static WebDriver getFFDriver(String profile) {
		WebDriver driver = null;
		System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.profile", profile);
		driver = new FirefoxDriver();
		return driver;
	}

	private static WebDriver getIEDriver() {
		WebDriver driver = null;
		System.setProperty("webdriver.ie.driver", "driver/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		return driver;
	}

	/**
	 * 暂停，不需要处理sleep的异常
	 * 
	 * @param seconds
	 */
	public static void pause(float seconds) {
		try {
			Thread.sleep(Math.round(seconds * 1000));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
