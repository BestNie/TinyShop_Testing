package test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tools.Drivers;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class Shopping{
	private static WebDriver driver;
	public static String mainwinHandle;

	@BeforeEach
	public void setUp() {
		driver=Drivers.ff(2);
		mainwinHandle = driver.getWindowHandle();
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@ParameterizedTest
	@DisplayName("购物业务")
	@CsvFileSource(resources = "/购物.csv", numLinesToSkip = 1, encoding = "UTF-8")
	public void shopping(Boolean login, Boolean Specifications, Boolean stock, Boolean address, Boolean balance) {
		driver.get("http://192.168.56.101/tinyshop/index.php?con=simple&act=login");
		driver.findElement(By.id("account")).click();
		if (balance == false)
			driver.findElement(By.id("account")).sendKeys("ax@163.com");
		else
			driver.findElement(By.id("account")).sendKeys("xia@163.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.cssSelector(".btn")).click();
		// 进入首页
		driver.findElement(By.linkText("首页")).click();
		// 商品详情
		driver.findElement(By.cssSelector(".col-1:nth-child(1) img")).click();
		// 商品规格
		if (Specifications == false) {
			driver.findElement(By.cssSelector("#product-buttons > .buy-now > span")).click();
			String text = driver.findElement(By.cssSelector(".msg > span:nth-child(2)")).getText();
			assertEquals("请选择您要购买的商品规格！", text);
		}
		driver.findElement(By.cssSelector(".spec-item:nth-child(2) li:nth-child(1) > span")).click();
		driver.findElement(By.cssSelector(".spec-item:nth-child(3) li:nth-child(1) > span")).click();
		if (stock == false) {
			driver.findElement(By.id("buy-num")).clear();
			driver.findElement(By.id("buy-num")).sendKeys("12");
			driver.findElement(By.cssSelector(".product-title")).click();
			assertEquals("11", driver.findElement(By.id("buy-num")).getAttribute("value"));
		}
		//    商品数量
		driver.findElement(By.id("buy-num")).clear();
		driver.findElement(By.id("buy-num")).sendKeys("5");
		driver.findElement(By.cssSelector("#product-buttons > .buy-now > span")).click();
		// 购物车
		driver.findElement(By.linkText("立即结算")).click();
		// 结算界面选地址
		if (address == false) {
			driver.findElement(By.cssSelector("p > .btn-main")).click();
			assertEquals("选择的地址信息不正确！", driver.findElement(By.cssSelector(".alert")).getText());
		}
		driver.findElement(By.cssSelector(".address-info > label")).click();
		// 立即结算
		driver.findElement(By.cssSelector("p > .btn-main")).click();
		// 立即支付
		driver.findElement(By.cssSelector(".btn-main")).click();
		Set<String> set = driver.getWindowHandles();
		Drivers.pause(1);
		if (balance == false) {
			// 跳转窗口
			for (String h : set) {
				if (driver.switchTo().window(h).findElements(By.cssSelector(".main")).size() > 0) {
					break;
				}
			}
			assertEquals("余额不足,请选择其它支付方式！", driver.findElement(By.cssSelector(".main")).getText());
		} else {
			// 跳转窗口
			for (String h : set) {
				if (driver.switchTo().window(h).findElements(By.cssSelector("#voucher-btn")).size() == 0) {
					break;
				}
			}
			Drivers.pause(1);
			assertEquals("支付成功，订购已完成！",
					driver.findElement(By.cssSelector(".status-bar > span:nth-child(1)")).getText());
		}

	}

	@Test
	@DisplayName("测试未登入购物")
	public void test1() {
		driver.get("http://192.168.56.101/tinyshop/index.php?con=index&act=index");
		driver.findElement(By.cssSelector(".col-1:nth-child(1) img")).click();
		driver.findElement(By.cssSelector(".spec-item:nth-child(2) li:nth-child(1) > span")).click();
		driver.findElement(By.cssSelector(".spec-item:nth-child(3) li:nth-child(1) > span")).click();
		driver.findElement(By.cssSelector("#product-buttons > .buy-now > span")).click();
		driver.findElement(By.linkText("立即结算")).click();
		assertEquals("登录", driver.findElement(By.cssSelector(".btn")).getText());
	}
}
