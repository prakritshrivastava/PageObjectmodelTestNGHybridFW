package com.opencart.qa.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.opencart.qa.factory.DriverFactory;
import com.opencart.qa.listeners.TestAllureListener;
import com.opencart.qa.pages.HomePage;
import com.opencart.qa.pages.LoginPage;
import com.opencart.qa.pages.ProductInfoPage;
import com.opencart.qa.pages.RegisterPage;
import com.opencart.qa.pages.ResultsPage;
import org.testng.annotations.Listeners;
import com.aventstack.chaintest.plugins.ChainTestListener;

@Listeners({ChainTestListener.class,TestAllureListener.class})
public class BaseTest {

	protected WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(@Optional("firefox") String browserName) {
		df=new DriverFactory(); //Create object of DriverFactory to access its methods
		prop=df.initProp(); //initialize the Properties from the method in DriverFactory class.
		
		if(browserName != null) { //If browserName is coming from xml
			prop.setProperty("browser", browserName);
		}
		
		//Pass the overriden value of the browser to the initalize driver method and obtain the driver instance.
		driver=df.initDriver(prop);
		//Now provide the initialized version of the driver 
		//to LoginPage
		loginPage=new LoginPage(driver);
	}
	
	@AfterMethod
	public void attachScreenshot(ITestResult result) {
		//Take SS for Failed Test Case
		if(!result.isSuccess()) {
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
	
	
	
}
