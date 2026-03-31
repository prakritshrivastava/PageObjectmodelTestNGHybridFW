package com.opencart.qa.pages;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class ProductInfoPage {

	/**
	 * Each class has it's own instance of WebDriver and creates object of element util to access the utility methods
	 */
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productMap;  
	/*
	 * The driver is initialized using the constructor
	 */
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	
	private final By header=By.cssSelector("div#content h1");
	private final By images=By.cssSelector("ul.thumbnails img");
	private final By quantity=By.name("quantity");
	private final By addToCart=By.id("button-cart");
	private final By metaData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By priceData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
			
	public String getProductHeader() {
		return eleUtil.doElementGetText(header);
	}
	
	public int getProductImagesCount() {
		int imagesCount=eleUtil.waitForVisibilityOfAllElements(images, AppConstants.SHORT_TIME_OUT).size();
		System.out.println("Total Images: "+imagesCount);
		return imagesCount;
	}
	
	public Map<String,String> getProductInfoData() {
		productMap=new LinkedHashMap<String,String>();
		productMap.put("ProductName", getProductHeader());
		productMap.put("ProductImages", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getPriceMetaData();
		return productMap;
	}
	
	/**
	 * Below are the values which need to be obtained from the page.
	 * Brand:Apple
		Product Code:Product 18
		Reward Points:800
		Availability:Out Of Stock
	 */
	private void getProductMetaData() {
		List<WebElement> metadataList=eleUtil.getElements(metaData);
		
		for(WebElement ele:metadataList) {
			String metaText=ele.getText();
			String[] meta=metaText.split(":");
			String metaKey=meta[0].trim();
			String metaValue=meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
		
	}
	
	private void getPriceMetaData() {
		List<WebElement> priceList=eleUtil.getElements(priceData);
		String productPrice=priceList.get(0).getText();
		//As the second price is like "Ex Tax: $2000" we need to get text --> split and capture it as 1st element of array.
		String productExtTaxPrice=priceList.get(1).getText().split(":")[1].trim();
		productMap.put("ProductPrice",productPrice);
		productMap.put("exTaxPrice",productExtTaxPrice);
	}
	
	
}
