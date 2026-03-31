package com.opencart.qa.pages;

import java.util.List;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class HomePage {

	/**
	 * Each class has it's own instance of WebDriver and creates object of element util to access the utility methods
	 */
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	/*
	 * The driver is initialized using the constructor
	 */
	public HomePage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	private final By logoutLink = By.linkText("Logout");
	private final By headers=By.cssSelector("div#content h2");
	private final By searchTextField = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button");
	
	public String getHomePageTitle() {
		String actTitle=eleUtil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.MEDIUM_TIME_OUT);
		return actTitle;
	}
	
	
	public boolean isLogoutExist() {
		return eleUtil.isElementDisplayed(logoutLink);
	}
	
	public List<String> getHomePageHeaders() {
		List<WebElement> headersList=eleUtil.waitForPresenceOfAllElements(headers, AppConstants.SHORT_TIME_OUT);
		List<String> headersValueList= new ArrayList<String>();
		
		for(WebElement e : headersList) {
			String text = e.getText();
			headersValueList.add(text);
		}
		
		return headersValueList;
	}
	
	public ResultsPage doSearch(String searchKey) {
		eleUtil.doSendKeys(searchTextField,searchKey);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);
	}
	
}
