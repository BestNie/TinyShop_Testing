package test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tools.Drivers;

import org.openqa.selenium.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

public class ChangeInformation {
	private static WebDriver driver;

	@BeforeAll
	public static void setUp() {
		driver=Drivers.ff();
		driver.get("http://192.168.56.101/tinyshop/index.php?con=index&act=index");
		driver.findElement(By.linkText("登录")).click();
		driver.findElement(By.id("account")).click();
		driver.findElement(By.id("account")).sendKeys("ax@163.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.cssSelector(".btn")).click();

	}

	@AfterAll
	public static void tearDown() {
		driver.quit();
	}

	@BeforeEach
	public void test() {
		driver.findElement(By.linkText("个人资料")).click();
	}

	@ParameterizedTest
	@DisplayName("用户修改 个人资料")
	@CsvFileSource(resources = "/个人资料.csv", numLinesToSkip = 1, encoding = "UTF-8")
	public void test1(String nc, String name, String bir, String tel, String nb, String sex, String jd, String shen,
			String shi, String qu, boolean expected) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		driver.findElement(By.name("name")).click();
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys(nc);
		driver.findElement(By.name("real_name")).click();
		driver.findElement(By.name("real_name")).clear();
		driver.findElement(By.name("real_name")).sendKeys(name);
		driver.findElement(By.name("birthday")).click();
		driver.findElement(By.name("birthday")).clear();
		driver.findElement(By.name("birthday")).sendKeys(bir);
		driver.findElement(By.name("mobile")).click();
		boolean alertpresent = true;
		Alert alert = null;
		try {
			alert = wait.withTimeout(Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
			alertpresent = false;
		}
		if (alertpresent) {
			alert.accept();
			assertEquals(expected, false);
		} else {
			driver.findElement(By.name("mobile")).clear();
			driver.findElement(By.name("mobile")).sendKeys(tel);
			if (sex == "男")
				driver.findElement(By.cssSelector("li.line:nth-child(8) > label:nth-child(5)")).click();
			else
				driver.findElement(By.cssSelector("li.line:nth-child(8) > label:nth-child(3)")).click();
			driver.findElement(By.name("phone")).clear();
			driver.findElement(By.name("phone")).sendKeys(nb);
			if (shen.equals("不填")) {
				driver.findElement(By.id("province")).click();
				driver.findElement(By.cssSelector("option:nth-child(1)")).click();
			} else {
				driver.findElement(By.id("province")).click();
				driver.findElement(By.cssSelector("option:nth-child(18)")).click();
			}
			
			if (shi.equals("不填")) {
				driver.findElement(By.id("city")).click();
				driver.findElement(By.cssSelector("#city > option:nth-child(1)")).click();
			} else {
				driver.findElement(By.id("city")).click();
				driver.findElement(By.cssSelector("#city > option:nth-child(13)")).click();
			}
			if (qu.equals("不填")) {
				driver.findElement(By.id("county")).click();
				driver.findElement(By.cssSelector("#county > option:nth-child(1)")).click();
			} else {
				driver.findElement(By.id("county")).click();
				driver.findElement(By.id("county")).findElement(By.xpath("//option[. = '曾都区']")).click();
			}
			
			driver.findElement(By.xpath("//textarea[@name='addr']")).clear();
			driver.findElement(By.xpath("//textarea[@name='addr']")).sendKeys(jd);
			driver.findElement(By.name("phone")).click();
			driver.findElement(By.cssSelector("input.btn")).click();
			List<WebElement> list = driver.findElements(By.cssSelector(
					"html body div#main div.container.list.blank div.row-5 div.col-4 div.alert.alert-fail"));
			int num = list.size();
			String text = "";
			for (WebElement we : list) {
				text = we.getText();
			}
			assertEquals(expected, num > 0 && text.contains("保存成功"));
		}
	}
}
