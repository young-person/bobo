package com.app.crawler.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

/**
 * @Description: TODO
 * @ClassName: Test
 */
public class Test {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:\\tmp\\chromedriver.exe");
		//ChromeDriverService driverService = ChromeDriverService.createDefaultService();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.dd18ll.space/");

		
		WebElement element = driver.findElement(By.cssSelector(".down_btn"));
		System.out.println(element);
		element.click();
		Set<String> names = driver.getWindowHandles();
		for (String s : names) {
			System.err.println(s);
			driver.switchTo().window(s);
			String url = driver.getCurrentUrl();
			if (url.indexOf("down2") > -1) {
				break;
			}
		}
		WebElement element2 = driver.findElement(By.cssSelector(".down_btn"));
		System.out.println(element2);
		element2.click();
//        driver.quit();
//        driver.close();

	}
}
