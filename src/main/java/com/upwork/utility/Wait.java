package com.upwork.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {

	public static void waitforXPath(WebDriver driver, String xpath,String duration) {
		
		try{
		Long lduration = Long.parseLong(duration);
		
		WebDriverWait wait = new WebDriverWait(driver, lduration);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		
		System.out.println("[WAIT] Waiting for xpath: " + xpath + " ...");
		
		} catch (Throwable e){
			System.out.println("[WAIT] Waiting for xpath: " + xpath + " to be visible has failed.");
		}
		
	}

	public static void waitforTitle(WebDriver driver, String pageTitle,
			String duration) {
		
		Long lduration = Long.parseLong(duration);
		
		WebDriverWait wait = new WebDriverWait(driver, lduration);
		wait.until(ExpectedConditions.titleContains(pageTitle));
		
		
	}

}
