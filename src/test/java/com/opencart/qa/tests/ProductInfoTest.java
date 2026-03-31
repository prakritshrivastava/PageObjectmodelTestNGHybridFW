package com.opencart.qa.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.opencart.qa.base.BaseTest;

public class ProductInfoTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetup() {
		homePage=loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@DataProvider
	public Object[][] getProductData(){
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"imac","iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"canon","Canon EOS 5D"},
		};
	}
	
	
	@DataProvider
	public Object[][] getProductImagesData(){
		return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"imac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"canon","Canon EOS 5D",3},
		};
	}
	
	//@Test(dataProvider="getProductData")
	public void productHeaderTest(String searchKey,String productName) {
		resultsPage=homePage.doSearch(searchKey);
		productInfoPage=resultsPage.selectProduct(productName);
		String productHeader=productInfoPage.getProductHeader();
		Assert.assertEquals(productHeader,productName);
	}
	
	//@Test(dataProvider="getProductImagesData")
	public void productImagesTest(String searchKey,String productName,int imagesCount) {
		resultsPage=homePage.doSearch(searchKey);
		productInfoPage=resultsPage.selectProduct(productName);
		int imagesActCount=productInfoPage.getProductImagesCount();
		Assert.assertEquals(imagesActCount,imagesCount);
	}
	
	@Test
	public void productInfoTest() {
		//The chaining needs to be done in each @Test method as each @Test method is executed independently
		resultsPage=homePage.doSearch("macbook");
		productInfoPage=resultsPage.selectProduct("MacBook Pro");
		Map<String,String> productInfoDataMap=productInfoPage.getProductInfoData();
		
		SoftAssert softAssert = new SoftAssert();
		
		//Using the keys which would be populated in runtime
		//Assert.assertEquals(productInfoDataMap.get("productname"),"MacBook Pro");
		softAssert.assertEquals(productInfoDataMap.get("Brand"),"Apple");
		softAssert.assertEquals(productInfoDataMap.get("Product Code"),"Product 18");
		softAssert.assertEquals(productInfoDataMap.get("Reward Points"),"800");
		softAssert.assertEquals(productInfoDataMap.get("Availability"),"Out Of Stock");
		softAssert.assertEquals(productInfoDataMap.get("exTaxPrice"),"$2,000.00");
		
		softAssert.assertAll();
	}
}
