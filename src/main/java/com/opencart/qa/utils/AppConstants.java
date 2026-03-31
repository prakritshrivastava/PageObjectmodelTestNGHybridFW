package com.opencart.qa.utils;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	//constants need not be accessed multi thread manner hence for ease of access they can be made static
	public static final String LOGIN_PAGE_TITLE="Account Login";
	public static final String HOME_PAGE_TITLE="My Account";
	public static final long SHORT_TIME_OUT=5;
	public static final long MEDIUM_TIME_OUT=10;
	public static final long LONG_TIME_OUT=30;
	public static final String URL_VALUE="route=account/login";
	
	public static final List<String> expHeadersList = Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");
	public static final String REGISTER_SUCCESS_MESSAGE = "Your Account Has Been Created!";
	
	public static final String TestDataSheetName="DataSheet";
	public static final String TestDataCSVName="DataForAccountCreationCSV";
}
