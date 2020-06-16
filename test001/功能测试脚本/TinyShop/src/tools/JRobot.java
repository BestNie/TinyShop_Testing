package tools;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 * @author tomJa
 *
 */
public class JRobot {
	private Robot robot;

	public JRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 下一个控件
	 */
	public void nextField() {
		robot.delay(1000);
		press(KeyEvent.VK_TAB);
	}

	/**
	 * 按回车
	 */
	public void enter() {
		robot.delay(1000);
		press(KeyEvent.VK_ENTER);
	}

	/**
	 * 下n个控件
	 */
	public void nextField(int n) {
		for (int i = 0; i < n; i++)
			nextField();
	}

	/**
	 * 输入字符串
	 * 
	 * @param text
	 */
	public void type(String text) {
		robot.delay(1000);
		writeToClipboard(text);
		pasteClipboard();
	}

	/**
	 * 同时按下几个键
	 * 
	 * @param vks KeyEvent类定义的虚拟键
	 */
	private void press(int... vks) {
		for (int vk : vks) {
			robot.keyPress(vk);
		}
		robot.delay(100);
		for (int i = vks.length - 1; i >= 0; i--) {
			robot.keyRelease(vks[i]);
		}
	}

	public void writeToClipboard(String s) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable transferable = new StringSelection(s);
		clipboard.setContents(transferable, null);
	}

	public void pasteClipboard() {
		press(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
	}
}
