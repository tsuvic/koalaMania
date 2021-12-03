package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/*
 * macOS Big Sur Ver11.6
 * Spring Tool Suite 4 Version: 4.8.1.RELEASE
 * OpenJDK version "17.0.1"
 * selenide:5.24.3-selenium-4.0.0-rc-1'
 * org.junit.jupiter:junit-jupiter:5.5.2
 * https://selenide.org/quick-start.html
 * https://www.seleniumqref.com/api/webdriver_gyaku.html
 */

public class SelenideTest {
	private void screenshot(WebDriver webDriver, StringBuilder path, int screenshotCount) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)webDriver;
		File file = ts.getScreenshotAs(OutputType.FILE);		
		
		var fileName = new StringBuilder();
		fileName.append(path);
		fileName.append(String.valueOf(screenshotCount));
		fileName.append(".png");
		
		FileUtils.copyFile(file, new File(fileName.toString()));
		System.out.println("the Screenshot is taken " + screenshotCount);
	}
	
	@Test
	void test1() throws IOException, InterruptedException {
		// Optional. If not specified, WebDriver searches the PATH for chrome driver.
		//If include the ChromeDriver location in your PATH environment variable, it's fine.
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);
		try {
			webDriver.get("http://koalamania.herokuapp.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//テスト実施日時フォルダの作成（繰り返し利用するメソッドではないため、スクショのようにモジュール化しない）
		var path = new StringBuilder();
		path.append("/Users/Oyama/dev/");
		
		var datetime = LocalDateTime.now();
		var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_kkmm");
		path.append(datetime.format(dateTimeFormatter));
		path.append("/");
		
		var folder = new File(path.toString());
		folder.mkdir();
		
		//スクショ向けカウンタ
		int screenshotCount = 1;
		screenshot(webDriver, path, screenshotCount++);
		
		webDriver.findElement(By.name("keyword")).sendKeys("多摩");
		Thread.sleep(3000);
		screenshot(webDriver, path, screenshotCount++);
		
		webDriver.findElement(By.className("topsearch_button")).click();
		Thread.sleep(5000);
		screenshot(webDriver, path, screenshotCount++);
		
		System.out.println(webDriver.getCurrentUrl());
		System.out.println(webDriver.getTitle());
		System.out.println(webDriver.getPageSource());
		
		//test selenide 
//		open("http://koalamania.herokuapp.com/search?keyword=%E5%A4%9A%E6%91%A9");
//		 $("[class=links]").$("[class=btn]").click();
		webDriver.findElement(By.className("btn-info")).click();
		Thread.sleep(5000);
		screenshot(webDriver, path, screenshotCount++);
		
		webDriver.quit();
	}
}
