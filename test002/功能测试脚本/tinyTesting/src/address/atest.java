package address;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class atest {
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
	@DisplayName("测试修改地址")
	@CsvFileSource(resources = "/地址.csv", numLinesToSkip = 1, encoding = "UTF-8")
	public void test1(Boolean mphone, Boolean phone) {
		driver.get("http://192.168.56.1/tinyshop/index.php?con=simple&act=login");
		// 登录
		driver.findElement(By.id("account")).click();
		driver.findElement(By.id("account")).sendKeys("1234@mm.com");
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.cssSelector(".btn")).click();
		driver.findElement(By.linkText("收货地址")).click();
		driver.findElement(By.id("address_other")).click();
		driver.switchTo().frame(0);
		driver.findElement(By.name("accept_name")).click();
		driver.findElement(By.name("accept_name")).sendKeys("liming");
		driver.findElement(By.name("mobile")).click();
		if (mphone == false) {
			driver.findElement(By.name("mobile")).sendKeys("187456123789");
			String text = driver.findElement(By.xpath("//li[2]//label[2]")).getText();
			assertEquals("手机号码格式错误", text);
			driver.quit();
		}
		driver.findElement(By.name("mobile")).sendKeys("18379856321");
		if (phone == false) {
			driver.findElement(By.name("phone")).sendKeys("123456789");
			String text = driver.findElement(By.xpath("//li[3]//label[2]")).getText();
			assertEquals("电话号码格式错误", text);
			driver.quit();
		}
		driver.findElement(By.name("phone")).click();
		driver.findElement(By.name("phone")).sendKeys("12345678");
		driver.findElement(By.id("province")).click();
		driver.findElement(By.xpath("//option[. = '北京市']")).click();
		driver.findElement(By.cssSelector("#province > option:nth-child(2)")).click();
		driver.findElement(By.id("city")).click();
		driver.findElement(By.xpath("//option[. = '北京市']")).click();
		driver.findElement(By.cssSelector("#city > option:nth-child(2)")).click();
		driver.findElement(By.id("county")).click();
		driver.findElement(By.xpath("//option[. = '东城区']")).click();
		driver.findElement(By.cssSelector("#county > option:nth-child(2)")).click();
		driver.findElement(By.name("zip")).click();
		driver.findElement(By.name("zip")).sendKeys("123456");
		driver.findElement(By.name("addr")).click();
		driver.findElement(By.name("addr")).sendKeys("dsfsdgg");
		driver.findElement(By.cssSelector(".btn")).click();

	}
}
