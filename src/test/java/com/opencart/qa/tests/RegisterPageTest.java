package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.CSVUtil;
import com.opencart.qa.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void registrationSetup() {
		registerPage=loginPage.navigateToRegisterPage();
	}
	

	@DataProvider
	public Object[][] getUserRegData(){
		return new Object[][] {
			{"raj","sharma","1345223349","test@nal.com","yes"},
			{"ravi","kushan","1345223350","testing@nal.com","no"},
			{"shreya","kadam","1545223350","testtwo@nal.com","yes"}
		};
	}
	
	/**
	 * Data provider for excel
	 * @return
	 */
	@DataProvider
	public Object[][] getUserRegDataFromExcel(){
		return ExcelUtil.outputExcel(AppConstants.TestDataSheetName);
	}
	
	/**
	 * Data provider for csv
	 * @return
	 */
	
	@DataProvider
	public Object[][] getUserRegDataFromCSV(){
		return CSVUtil.getDataFromCSV(AppConstants.TestDataCSVName);
	}
	
	/**
	 * Execute the registration.
	 * @param firstName
	 * @param lastName
	 * @param telephone
	 * @param password
	 * @param subscribe
	 */
	@Test(dataProvider="getUserRegDataFromCSV")
	public void userRegisterPage(String firstName,String lastName,String telephone,String password,String subscribe) {
		Assert.assertTrue(registerPage.userRegistration(firstName,lastName,telephone,password,subscribe));
	}
}
