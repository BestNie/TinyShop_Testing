package tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Drivers {

	public static WebDriver ff() {
		System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
		return new FirefoxDriver();
	}


	public static WebDriver ff(int waitsec) {
		WebDriver driver = ff();
		driver.manage().timeouts().implicitlyWait(waitsec, TimeUnit.SECONDS);
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

	/**
	 * 得到页面的截图
	 * 
	 * @param driver
	 * @return 返回BufferedImage对象
	 */
	public static BufferedImage getScreen(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new ByteArrayInputStream(ts.getScreenshotAs(OutputType.BYTES)));
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return bi;
	}

	/**
	 * 把页面的截图保存为指定的文件，图片格式是png的
	 * 
	 * @param driver
	 * @param file   指定的文件后缀最好是png
	 * @return 返回传入的file
	 */
	public static File saveScreen(WebDriver driver, File file) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File tmp = ts.getScreenshotAs(OutputType.FILE);
		if (file.exists() && !file.delete())
			throw new RuntimeException("原有文件无法删除");
		tmp.renameTo(file);
		return file;
	}
}
