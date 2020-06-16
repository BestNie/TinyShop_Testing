package tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class Nodes {
	public static WebElement fill( CharSequence keysToSend,WebElement e) {
		e.click();
		e.clear();
		e.sendKeys(keysToSend);
		return e;
	}
	/**
	 * 得到元素的截图
	 * 
	 * @param element
	 * @return 返回BufferedImage对象
	 */
	public static BufferedImage getElementImg(WebElement element) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new ByteArrayInputStream(element.getScreenshotAs(OutputType.BYTES)));
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return bi;
	}
	/**
	 * 把元素的截图保存为指定的文件，图片格式是png的
	 * 
	 * @param element 要截图的元素
	 * @param file 指定的文件后缀最好是png
	 * @return 返回传入的file
	 */
	public static File saveElementImg(WebElement element, File file) {
		File tmp = element.getScreenshotAs(OutputType.FILE);
		if (file.exists() && !file.delete())
			throw new RuntimeException("原有文件无法删除");
		tmp.renameTo(file);
		return file;
	}
}
