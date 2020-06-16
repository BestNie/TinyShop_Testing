package fshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class fshopping {
	private static WebDriver driver;
	public static String mainwinHandle;

	@BeforeEach
	public void setUp() {
		driver = new FirefoxDriver();
		mainwinHandle = driver.getWindowHandle();
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@ParameterizedTest
	@DisplayName("测试未支付抢购")
	@CsvFileSource(resources = "/购物.csv", numLinesToSkip = 1, encoding = "UTF-8")
	public void test1(Boolean Specifications) {
		driver.get("http://127.0.0.1/tinyshop/index.php?con=simple&act=login");
		// 登录
		driver.findElement(By.id("account")).click();
		driver.findElement(By.id("account")).sendKeys("1234@mm.com");
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.cssSelector(".btn")).click();
		driver.findElement(By.linkText("限时抢购")).click();
		// 查看商品
		driver.findElement(By.cssSelector("div.header.img a:nth-child(1) > img:nth-child(1)")).click();
		// 选择商品规格
		if (Specifications == false) {
			String text = driver.findElement(By.cssSelector(".msg > span:nth-child(2)")).getText();
			assertEquals("请选择您要购买的商品规格！", text);
		}
		driver.findElement(By.cssSelector(".spec-item:nth-child(2) li:nth-child(1) > span")).click();
		driver.findElement(By.cssSelector(".spec-item:nth-child(3) li:nth-child(2) > span")).click();
		driver.findElement(By.id("buy-now")).click();
		// 提交订单
		driver.findElement(By.cssSelector(".address-list > li:nth-child(2)")).click();
		driver.findElement(By.cssSelector(".fr > .btn")).click();
		driver.findElement(By.cssSelector(".btn-main")).click();
		driver.findElement(By.cssSelector("p > .btn-main")).click();
		// 支付
		driver.findElement(By.cssSelector(".btn-main")).click();
		driver.findElement(By.cssSelector(" a:nth-child(1)")).click();// 安全退出
	}

	@Test
	@DisplayName("测试支付抢购")
	public void test2() {
		driver.get("http://127.0.0.1/tinyshop/index.php?con=simple&act=login");
		// 登录
		driver.findElement(By.id("account")).click();
		driver.findElement(By.id("account")).sendKeys("1.1@cc.com");
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.cssSelector(".btn")).click();
		driver.findElement(By.linkText("限时抢购")).click();
		// 查看商品
		driver.findElement(By.cssSelector("div.header.img a:nth-child(1) > img:nth-child(1)")).click();
		// 选择商品规格
		driver.findElement(By.cssSelector(".spec-item:nth-child(2) li:nth-child(1) > span")).click();
		driver.findElement(By.cssSelector(".spec-item:nth-child(3) li:nth-child(2) > span")).click();
		driver.findElement(By.id("buy-now")).click();
		// 提交订单
		driver.findElement(By.cssSelector(".address-list > li:nth-child(2)")).click();
		driver.findElement(By.cssSelector(".fr > .btn")).click();
		driver.findElement(By.cssSelector(".btn-main")).click();
		driver.findElement(By.cssSelector("p > .btn-main")).click();
		// 支付
		driver.findElement(By.cssSelector(".btn-main")).click();
		driver.findElement(By.cssSelector(".status-bar > span:nth-child(1)")).click();
		driver.findElement(By.cssSelector(" a:nth-child(1)")).click();// 安全退出
	}

}
