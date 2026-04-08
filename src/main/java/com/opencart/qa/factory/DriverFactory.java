package com.opencart.qa.factory;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.opencart.qa.exceptions.BrowserExceptions;
import com.opencart.qa.exceptions.FrameworkException;

public class DriverFactory {

	
	/**
	 * The WebDriver should not be static as then only one copy of the same would be stored in CMA memory and would not be usable for parallel execution.
	 */
	WebDriver driver;
	Properties prop;
	
	public static String highlight;
	public OptionsManager optionsManager;
	
	//With threadlocal two options are available: get and set
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * Below method must be called only after the properties has been initialized.
	 * @param prop
	 * @return
	 */
	
	public WebDriver initDriver(Properties prop) {
		
		String browserName=prop.getProperty("browser");
		highlight=prop.getProperty("highlight");
		optionsManager=new OptionsManager(prop);
		
		switch(browserName.trim().toLowerCase()) {
		
			case "chrome":
				if(Boolean.parseBoolean(prop.getProperty("remote"))) {
					initRemoteDriver(browserName);
				}
				else {
					//driver=new ChromeDriver(optionsManager.getChromeOptions());
					//below chromedriver object is initialized by thread local driver
					//this allows any thread to get fresh and dedicated copy of the driver
					tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));	
				}
				break;
			case "edge":
				if(Boolean.parseBoolean(prop.getProperty("remote"))) {
					initRemoteDriver(browserName);
				}
				else {
					//driver=new ChromeDriver(optionsManager.getChromeOptions());
					//below chromedriver object is initialized by thread local driver
					//this allows any thread to get fresh and dedicated copy of the driver
					tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));	
				}
				break;
			case "firefox":
				if(Boolean.parseBoolean(prop.getProperty("remote"))) {
					initRemoteDriver(browserName);
				}
				else {
					//driver=new ChromeDriver(optionsManager.getChromeOptions());
					//below chromedriver object is initialized by thread local driver
					//this allows any thread to get fresh and dedicated copy of the driver
					tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));	
				}
				break;
			default:
				System.out.println("Enter valid Browser: chrome/edge/firefox");
				throw new BrowserExceptions("Invalid Browser Entered");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		
		return getDriver();
		
	}
	
	/**
	 * Return one fresh and dedicated copy of the Thread
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	private void initRemoteDriver(String browserName) {
		System.out.println("Running via Remote WebDriver");
		
		try {
			switch(browserName.trim().toLowerCase()) {
			case "chrome":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getFireFoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getEdgeOptions()));
				break;
			default:
				System.out.println("Please supply correct browser name: chrome/firefox/edge");
			}
		}
		catch(MalformedURLException exp) {
			System.out.println("Malformed URL Exception: "+exp.getMessage());
		}
		
	}
	
	/**
	 * Below method has to be called to initialize the property reading
	 * @return
	 */
	public Properties initProp() {
		
		FileInputStream fis=null;
		String envName=System.getProperty("env");
		System.out.println("Env name is: "+envName);
	
		try {
			
			if(envName == null) {
				System.out.println("Since env name is null running on QA environment");
				envName="qa";
				fis= new FileInputStream("./src/test/resources/config/config.qa.properties");
			}
			
			switch(envName.trim().toLowerCase()) {
			case "qa":
				fis= new FileInputStream("./src/test/resources/config/config.qa.properties");
				break;
				
			case "dev":
				fis= new FileInputStream("./src/test/resources/config/config.dev.properties");
				break;
			
			case "stage":
				fis= new FileInputStream("./src/test/resources/config/config.stage.properties");
				break;
			
			case "prod":
				fis= new FileInputStream("./src/test/resources/config/config.properties");
				break;
			
			default:
				System.out.println("***********Invalid environment Name**********");
				throw new FrameworkException("Invalid Env Name");
			}	
		
			prop=new Properties();
			prop.load(fis);
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return prop;
	}
	
	public static File getScreenshotFile() {
		File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}
	
	/**
	 * Output of ByteArray is lighter than file
	 * @return
	 */
	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES);
	}
	
	public static String getScreenshotBase64() {
		return ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BASE64);
	}
	
}

