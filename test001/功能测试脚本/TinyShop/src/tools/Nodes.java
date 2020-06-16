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
	 * �õ�Ԫ�صĽ�ͼ
	 * 
	 * @param element
	 * @return ����BufferedImage����
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
	 * ��Ԫ�صĽ�ͼ����Ϊָ�����ļ���ͼƬ��ʽ��png��
	 * 
	 * @param element Ҫ��ͼ��Ԫ��
	 * @param file ָ�����ļ���׺�����png
	 * @return ���ش����file
	 */
	public static File saveElementImg(WebElement element, File file) {
		File tmp = element.getScreenshotAs(OutputType.FILE);
		if (file.exists() && !file.delete())
			throw new RuntimeException("ԭ���ļ��޷�ɾ��");
		tmp.renameTo(file);
		return file;
	}
}
