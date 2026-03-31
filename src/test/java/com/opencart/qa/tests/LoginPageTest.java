package com.opencart.qa.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Login Page for OpenCart Application")
@Story("Login US 200: Add Login Page Features with Title,URL,LoginPage etc")
public class LoginPageTest extends BaseTest {
	
	@Description("Login Page Title")
	@Severity(SeverityLevel.TRIVIAL)
	@Test
	public void loginPageTitleTest() {
		Assert.assertEquals(loginPage.getPageTitle(), AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Description("Login Page URL")
	@Severity(SeverityLevel.CRITICAL)
	@Issue("TEST-1234")
	@Test
	public void loginPageURLTest() {
		Assert.assertTrue(loginPage.getTheLoginPageURL().contains(AppConstants.URL_VALUE));
	}
	/**
	 * Hack to ensure that a certan test executes in the end is
	 * to use maximum value of integer
	 */
	@Description("Login Page URL")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void testLogin() {
		homePage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE);	}
	}


