package com.ReusableFunctions;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.framework.BaseTest;
import com.framework.Generic;

public class ReuseFunctions {


	//=========>
	Generic oGenericUtils = new Generic();	

//Locators--------> 
	String acceptCookies 	= 	"//button[@data-testid='close-button']";

	
	
	

	//===========================Launch Application===========================================================>
	public WebDriver LaunchApplication(String sURL,HashMap<String, String> XLTestData,BaseTest basetest) {
		WebDriver driver = null;
		System.out.println("Path "+System.getProperty("user.home")+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
		try {

			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\resources\\chromedriver.exe");

			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			driver.get(sURL);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			Thread.sleep(10000);
			driver.findElement(By.xpath(acceptCookies)).click();
		
		}catch(Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(), "FAILED",basetest);
			basetest.test.log(Status.FAIL,"Tommy Application is not Launched");
			driver.quit();
			Assert.assertTrue(false);
		}
		return driver;
	}	


	//Verify Lunch-------------->	
	public void VerifyLaunchURL(WebDriver driver, HashMap<String, String> XLTestData, BaseTest basetest)
			throws InterruptedException {
		try {
			
			if(driver.findElements(By.xpath(acceptCookies)).size()>0)
			{
				
			basetest.test.log(Status.PASS, "Application Launched successfully");
			
			}
			else 
			{
			basetest.test.log(Status.FAIL, "Application not Launched successfully");	
		
			}

		}
		catch (Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
			basetest.test.log(Status.FAIL, "test failed");
			basetest.test.log(Status.FAIL, "Application not Launched successfully");
			driver.quit();
			Assert.assertTrue(false);
		}
	}	

//Login
	
	public void LoginApp(WebDriver driver, HashMap<String, String> XLTestData, BaseTest basetest)
			throws InterruptedException {
		try {



		}
		catch (Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
			basetest.test.log(Status.FAIL, "test failed");
			driver.quit();
			Assert.assertTrue(false);
		}
	}	
	
	
	
	
}