package com.framework;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.relevantcodes.extentreports.LogStatus;


public class ScreenShot {
	  BaseTest basetest;
	
	public static String getScreenshot(WebDriver webdriver) throws Exception{
	
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);//Convert web driver object to TakeScreenshot
		
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);//Call getScreenshotAs method to create image file
		
		String filename =  new SimpleDateFormat("yyyyMMddhhmmss'.txt'").format(new Date());
		
		Date now = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd  hh mm ss");
		
		String time = dateFormat.format(now);
		
		time = time.replace(" ", "");

		String path ="./target/ScreenShots/"+time+".png";

		File DesFile =new File(path); //Move image file to new destination
		
		FileUtils.copyFile(SrcFile, DesFile);

		return path ;
	}

	}
	
