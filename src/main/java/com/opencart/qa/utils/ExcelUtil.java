package com.opencart.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	
	private static final String TEST_DATA_SHEET_PATH="./src/test/resources/testdata/DataForAccountCreation.xlsx";
	private static Workbook book;
	private static org.apache.poi.ss.usermodel.Sheet sheet;
	
	
	public static Object[][] outputExcel(String sheetName) {
		
		Object[][] excelData = null;
		
		try {
			FileInputStream test_data_file_stream = new FileInputStream(TEST_DATA_SHEET_PATH);
			book=WorkbookFactory.create(test_data_file_stream);
			sheet=book.getSheet(sheetName);
		
			excelData=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			//Iterator for row
			for(int i=0;i<sheet.getLastRowNum();i++) {
				//Iterator for column
				for(int j=0;j<sheet.getRow(0).getLastCellNum();j++) {
					excelData[i][j]=sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
			
		}catch(FileNotFoundException fnfe) {
			System.out.println("File Not found exception:"+fnfe.getMessage());
		}
		catch(IOException ioExp) {
			System.out.println("IO Exception:"+ioExp.getMessage());
		}
		
		return excelData;
		
		
	}
	
	
	
	
	
	
}
