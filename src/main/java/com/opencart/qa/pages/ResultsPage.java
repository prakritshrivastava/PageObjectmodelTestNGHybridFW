package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class ResultsPage {

	
	/**
	 * Each class has it's own instance of WebDriver and creates object of element util to access the utility methods
	 */
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	/*
	 * The driver is initialized using the constructor
	 */
	public ResultsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	
	private final By searchResults=By.cssSelector("div.product-thumb");
	
	
	public int getSearchResultsProductCount() {
		int resultsCount=eleUtil.waitForVisibilityOfAllElements(searchResults, AppConstants.MEDIUM_TIME_OUT).size();
		System.out.println("Total results on Result Page:"+resultsCount);
		return resultsCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Product Name: "+productName);
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}
	
	
}
