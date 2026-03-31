package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

import io.qameta.allure.Step;


public class LoginPage {

	/**
	 * Each class has it's own instance of WebDriver and creates object of element util to access the utility methods
	 */
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	/*
	 * The driver is initialized using the constructor
	 */
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	/**
	 * Encapsulation concept is applied below, locators should not
	 * be accessed outside of the class and should be constant(final).
	 */
	private final By userNameText=By.cssSelector("#input-email");
	private final By passwordText=By.cssSelector("#input-password");
	private final By loginButton=By.cssSelector("[value='Login']");
	private final By forgotPassLink=By.xpath("//div[@class=\"form-group\"]//a[contains(text(),'Forgotten Password')]");
	private final By registerLink=By.linkText("Register");
	
	@Step("Get Page Title Step")
	public String getPageTitle() {
		String actTitle=eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE,AppConstants.MEDIUM_TIME_OUT);
		System.out.println("Login Page Title: "+actTitle);
		return actTitle;
	}
	
	@Step("Get Login Page URL")
	public String getTheLoginPageURL() {
		String actTitle=eleUtil.waitForURLContains(AppConstants.URL_VALUE, AppConstants.MEDIUM_TIME_OUT);
		return actTitle;
	}
	
	public boolean isForgotPwdLinkExist() {
		return eleUtil.waitForElementVisibility(forgotPassLink, AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}
	
	/**
	 * Below method will return the homepage class instance this is called Page Chaining concept.
	 * @param username
	 * @param pwd
	 * @return
	 */
	public HomePage doLogin(String username,String pwd) {
		System.out.println("Checking login for user: "+username);
		eleUtil.performSendKeys(userNameText, AppConstants.MEDIUM_TIME_OUT, username);
		eleUtil.performSendKeys(passwordText, AppConstants.MEDIUM_TIME_OUT, pwd);
		eleUtil.doClick(loginButton);
		return new HomePage(driver);
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForElementClickable(registerLink, AppConstants.MEDIUM_TIME_OUT);
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
	
}
