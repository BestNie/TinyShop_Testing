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
	 * ��ͣ������Ҫ����sleep���쳣
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
	 * �õ�ҳ��Ľ�ͼ
	 * 
	 * @param driver
	 * @return ����BufferedImage����
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
	 * ��ҳ��Ľ�ͼ����Ϊָ�����ļ���ͼƬ��ʽ��png��
	 * 
	 * @param driver
	 * @param file   ָ�����ļ���׺�����png
	 * @return ���ش����file
	 */
	public static File saveScreen(WebDriver driver, File file) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File tmp = ts.getScreenshotAs(OutputType.FILE);
		if (file.exists() && !file.delete())
			throw new RuntimeException("ԭ���ļ��޷�ɾ��");
		tmp.renameTo(file);
		return file;
	}
}
