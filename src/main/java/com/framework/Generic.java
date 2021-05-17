package com.framework;



import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import junit.framework.Assert;

public class Generic

{	
	ScreenShot screen =new ScreenShot();
	BaseTest basetest;

	public String getTextOfElement(WebDriver driver, String xpathExpress)
	{
		String textgeted= null;
		try {

			Thread.sleep(5000);
			WebElement element = driver.findElement(By.xpath(xpathExpress));
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOf((element)));
			textgeted =driver.findElement(By.xpath(xpathExpress)).getText();

		}catch (Exception e) {
			basetest.test.log(Status.FAIL, "text of the focused element("+xpathExpress+") not received ");
			driver.close();
			driver.quit();
			Assert.assertTrue(false);
		}

		return textgeted;
	}

	public  void Verify(String StepDetails,String sStatus) {

		if(sStatus.equalsIgnoreCase("PASSED")) {
			System.out.println(StepDetails);
			//basetest.test.log(Status.PASS,  StepDetails);
			Assert.assertTrue(StepDetails, true);
		}else {
			System.out.println(StepDetails);
			//basetest.test.log(Status.FAIL,  StepDetails);
			Assert.assertTrue(StepDetails, false);			
		}
	}

	public  void Verify(String StepDetails,String sStatus,BaseTest basetest) {

		if(sStatus.equalsIgnoreCase("PASSED")) {
			System.out.println(StepDetails);
			basetest.test.log(Status.PASS, StepDetails);			
			Assert.assertTrue(StepDetails, true);
		}else {
			System.out.println(StepDetails);
			basetest.test.log(Status.FAIL, StepDetails);		
			Assert.assertTrue(StepDetails, false);			
		}
	}

	//********************************CLICK BUTTON***********************************************************
	public  boolean clickButton(WebDriver driver,By sObjectType,String sText,BaseTest basetest) {
		boolean blnResult=false;
		String pagetitle = null;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							pagetitle = driver.getTitle();
							WebDriverWait wait = new WebDriverWait(driver,60);
							wait.until(ExpectedConditions.elementToBeClickable(sList.get(i)));
							sList.get(i).click();
							blnResult=true;
							Verify(sText+" is clicked sucessfully/in "+pagetitle+" page","PASSED",basetest);
							break;
						}
					}
				}
				if(!blnResult){
					Thread.sleep(1000);iTimer=iTimer+1;
				}else
					System.out.println(sText);
			}while ((blnResult!=true) && (iTimer!=60));

			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is not found/in "+pagetitle+" page","FAILED",basetest);
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sText+" is not Edited/Clicked successfully/in "+pagetitle+" page <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");

			}
		}catch(Exception e) {
			Verify(sText+"  is not found/in "+pagetitle+" page","FAILED",basetest);
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>'"+sText+"' is not Edited/Clicked successfully/in "+pagetitle+" page"+"'</span>");
			driver.quit();
			Assert.assertTrue(false);
		}
		return blnResult;
	}


	//********************************SET Value***********************************************************
	public  boolean SetVal(WebDriver driver,By sObjectType,String sInputVal,String sText,BaseTest basetest) {
		boolean blnResult=false;
		String pagetitle = null;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							pagetitle = driver.getTitle();
							WebDriverWait wait = new WebDriverWait(driver,60);
							wait.until(ExpectedConditions.elementToBeClickable(sList.get(i)));
							sList.get(i).clear();
							sList.get(i).sendKeys(sInputVal);
							blnResult=true;
							Verify(sInputVal  +" is Entered Successfully in "+ sText + "/in" +pagetitle +"Page","PASSED",basetest);
							break;
						}
					}
				}
				if(!blnResult) {
					Thread.sleep(1000);iTimer=iTimer+1;
				}else
					System.out.println(sText);
			}while ((blnResult!=true) && (iTimer!=60));

			//Flag returns false
			if(blnResult!=true) {
				Verify(sInputVal  +" is not Entered in "+ sText + "/in" +pagetitle +"Page","FAILED",basetest);
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sInputVal+" is not Entered/Selected successfully <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");

			}
		}catch(Exception e) {
			Verify(sText+"  is not found","FAILED",basetest);
			basetest.test.log(Status.FAIL,""+sInputVal+" is not Entered in"+ sText +"in" +pagetitle+"");
			driver.quit();
			Assert.assertTrue(false);
		}
		return blnResult;
	}


	//**********************************isElementExist*********************************************************
	public boolean isElementExist(WebDriver driver,By sObjectType,String sText,BaseTest basetest) {
		boolean blnResult=false;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							blnResult=true;
							Verify(sText+" is displayed Successfully","PASSED",basetest);
							basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+sText+"'</span>");
							break;
						}
					}
				}
				if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;}
			}while ((blnResult!=true) && (iTimer!=60));
			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is not found","FAILED",basetest);
				basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+sText+"'</span>");
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sText+" is not found <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");
				driver.quit();
				Assert.assertTrue(false);
			}
		}catch(Exception e) {
			Verify(sText+" is not displayed on "+sText,"FAILED",basetest);
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+sText+"'</span>");
			driver.quit();
			Assert.assertTrue(false);
		}
		return blnResult;
	}

	//********************************MoveToElement***********************************************************
	public void navigateMouseToElement(WebDriver driver,By sObjectType,String sInputval,BaseTest basetest) throws Exception
	{
		try {
			Thread.sleep(3000);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(sObjectType));
			WebElement webElement = driver.findElement(sObjectType);
			Actions builder = new Actions(driver);
			builder.moveToElement(webElement).perform();
			Thread.sleep(5000);
			System.out.println("Sucessfully Mouse hovered to "+sInputval);
			Verify("Sucessfully Mouse hovered to "+sInputval,"PASSED",basetest);
		}catch(Exception e) {
			Verify(sInputval+" is not found","FAILED",basetest);
			String FileName = screen .getScreenshot(driver);
			basetest.test.log(Status.FAIL,""+sInputval+" is not found <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");
			driver.quit();
			Assert.assertTrue(false);
		}

	}


	//**********************************isElementExistTime*********************************************************
	public boolean WaitUntilElement(WebDriver driver,By sObjectType,String sText) {
		boolean blnResult=false;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {

							blnResult=true;
							System.out.println("Loading Popup:="+i);
							Verify(sText+" is Displayed Sucessfully","PASSED");
							break;

						}
					}


				}
				if(!blnResult) {Thread.sleep(5000);iTimer=iTimer+1;
				}else {
					System.out.println(sText);
				}
			}while ((blnResult!=true) && (iTimer!=50));
			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is Not Displayed Sucessfully","FAILED");
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sText+" is Not Displayed Sucessfully <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");
			}
		}catch(Exception e) {
			Verify(sText+" is not found","FAILED");
			Assert.assertTrue(false);
			driver.quit();
		}
		return blnResult;
	}


	//=============================================SetvalueEnter=========================================================>
	public  boolean SetValEnter(WebDriver driver,By sObjectType,String sInputVal,String sText,BaseTest basetest) {
		boolean blnResult=false;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							sList.get(i).clear();
							sList.get(i).sendKeys(sInputVal);
							Robot robot = new Robot();
							robot.keyPress(KeyEvent.VK_ENTER);
							robot.keyRelease(KeyEvent.VK_ENTER);
							robot.delay(200);
							blnResult=true;
							Verify(sInputVal+"is Entered Successfully in "+sText,"PASSED",basetest);
							break;
						}
					}
				}
				if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;
				}else
					System.out.println(sText);
			}while ((blnResult!=true) && (iTimer!=30));

			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is not found","FAILED",basetest);
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sInputVal+" is not Entered/Selected successfully <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");
			}
		}catch(Exception e) {
			Verify(sText+"  is not found","FAILED",basetest);
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>'"+sText+"' is not Entered/Selected successfully"+"'</span>");
			driver.quit();
			Assert.assertTrue(false);
		}
		return blnResult;
	}

	//===============================================is Alert Present===============================================================================>
	public boolean isAlertPresent(WebDriver driver) {

		boolean presentFlag = false;

		try {

			// Check the presence of alert
			Alert alert = driver.switchTo().alert();
			
			// Alert present; set the flag
			presentFlag = true;
			// if present consume the alert
			String Error = alert.getText();
			basetest.test.log(Status.INFO,"<span style='font-weight:bold;color:blue'> '"+Error+"'</span>"+"Pop Message Displyed ");

		} catch (NoAlertPresentException ex) {
			// Alert not present
			//  ex.printStackTrace();
			System.out.println("Alert not found");
		}

		return presentFlag;
	}
	

	//==============>Alert handling========>	
	public boolean isAlertPresents(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		}// try
		catch (Exception e) {
			return false;
		}// catch
	}

	//=================================Properties file ===================================================>
	public String getPropertyValue(String propName) throws IOException
	{

		String propValue = "";

		try
		{
			
		InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\resources\\config.properties");
		Properties prop = new Properties();
		prop.load(input);
		propValue = prop.getProperty(propName);
		return(propValue);
		}
		catch(Exception e)
		{
			Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
			basetest.test.log(Status.FAIL, "test failed");
			Assert.assertTrue(false);
		}
		return(propValue);
	}

	//===========================>Java Script execuator to click the element=========>
	public  boolean clickButtonUsingJavaExecuator(WebDriver driver,By sObjectType,String sText,BaseTest basetest) {
		boolean blnResult=false;
		String pagetitle = null;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							pagetitle = driver.getTitle();
							WebDriverWait wait = new WebDriverWait(driver,60);
							wait.until(ExpectedConditions.elementToBeClickable(sList.get(i)));
							((JavascriptExecutor) driver).executeScript("arguments[0].click();", sList.get(i));
							blnResult=true;
							Verify(sText+" is clicked sucessfully/in "+pagetitle+" page","PASSED",basetest);
							break;
						}
					}
				}
				if(!blnResult){
					Thread.sleep(1000);iTimer=iTimer+1;
				}else
					System.out.println(sText);
			}while ((blnResult!=true) && (iTimer!=60));

			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is not found/in "+pagetitle+" page","FAILED",basetest);
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sText+" is not Edited/Clicked successfully/in "+pagetitle+" page <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");

			}
		}catch(Exception e) {
			Verify(sText+"  is not found/in "+pagetitle+" page","FAILED",basetest);
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>'"+sText+"' is not Edited/Clicked successfully/in "+pagetitle+" page"+"'</span>");
			driver.quit();
			Assert.assertTrue(false);
		}
		return blnResult;
	}
	
	
	public boolean existsElement(WebDriver driver, By id) {
		try {
			
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			driver.findElement(id);
			
		} catch (Exception e) 
		
		{
			return false;
		
		}
		return true;
	}

}