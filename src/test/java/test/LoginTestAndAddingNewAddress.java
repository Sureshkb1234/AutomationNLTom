package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ReusableFunctions.ReuseFunctions;
import com.aventstack.extentreports.Status;
import com.framework.BaseReport;
import com.framework.BaseTest;
import com.framework.Excel_Reader;
import com.framework.Generic;

import junit.framework.Assert;

public class LoginTestAndAddingNewAddress extends BaseReport{

	//===============================>
	private BaseTest basetest;
	public static Excel_Reader excelReader;
	public static int i=1;
	int HistoryRowNumber=0;
	int passCount=0, FailCount=0;
	public static String TestDataPath="";
	public static HashMap<String,String> XLTestData;
	public HashMap<String, String> CustomerDetailsData;
	public static WebDriver driver;

	//========================>
	ReuseFunctions oReuseFunctions=new ReuseFunctions();
	Generic oGenericUtils = new Generic();
	//========================>
	@BeforeTest(alwaysRun=true)
	public void getTest() throws IOException {
		basetest=new BaseTest();
		basetest.getTest(this.getClass().getSimpleName(),"Login Test");

	}

	//============================>
	@BeforeClass
	public void test() throws FileNotFoundException, IOException {
		TestDataPath = System.getProperty("user.dir") + "\\Data\\LoginTestData.xlsx";
		System.out.println("Test Data Path: "+TestDataPath);
		excelReader=new Excel_Reader(TestDataPath);
		excelReader.cFileNameWithPath = TestDataPath;
		excelReader.cSheetName = "TestData";
		excelReader.cTcID = "TestCaseID";
		excelReader.cTcValue = "1";
		XLTestData = new HashMap<String, String>();
		CustomerDetailsData = new HashMap<String, String>();
		XLTestData = excelReader.readExcel("TC_NST_" + Integer.toString(i));
	}

	//==============================>	
	@Test
	public void CustomerCreation() throws Exception {

		try {

			basetest.test = basetest.extent.createTest("Login_"+XLTestData.get("Scenario").toString(),"Login_"+XLTestData.get("Scenario").toString());

			//Application Launch
			String URL = oGenericUtils.getPropertyValue("sURL");
			oReuseFunctions.LaunchApplication(URL, XLTestData, basetest);

			//Verify the successfully Application Launch or not 
			oReuseFunctions.VerifyLaunchURL(driver, XLTestData, basetest);









		}
		catch (Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(), "FAILED",basetest);
			driver.quit();
			Assert.assertTrue(false);
		}
	}

	//=====================>
	@AfterMethod(alwaysRun = true)
	public void ExtentReport() {
		basetest.extent.flush();
	}


	//==================================>  
	@AfterClass(alwaysRun = true)
	public void LogsOut() throws InterruptedException, IOException {
		String ClassName = this.getClass().getSimpleName();
		LogScenario(ClassName, passCount, FailCount);
		driver.quit();

	}

}