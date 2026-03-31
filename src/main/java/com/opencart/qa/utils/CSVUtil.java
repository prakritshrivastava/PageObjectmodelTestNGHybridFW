package com.opencart.qa.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVUtil {

	private static String CSV_DATA_PATH="./src/test/resources/testdata/";
	private static List<String[]> rows;
	
	public static Object[][] getDataFromCSV(String csvFileName){
		
		String csvFullFile=null;
		
		if(!csvFileName.endsWith(".csv")) {
			csvFullFile=CSV_DATA_PATH+csvFileName+".csv";
		}
		else {
			csvFullFile=CSV_DATA_PATH+csvFileName;
		}
		
		
		CSVReader reader;
		try {
			reader=new CSVReader(new FileReader(csvFullFile));
			rows=reader.readAll();
			reader.close();
		}
		catch(FileNotFoundException fnfe) {
			System.out.println("File Not Found: "+fnfe.getMessage());
		}
		catch(IOException ioExp) {
			System.out.println("IO Exception: "+ioExp.getMessage());
		}
		catch(CsvException csvExp) {
			System.out.println("CSV Exception: "+csvExp.getMessage());
		}
		
		//Assemble the data into an Object array in order to return the same to the calling method.
		Object[][] data = new Object[rows.size()][];
		
		for(int i=0;i<rows.size();i++) {
			data[i]=rows.get(i);
		}
		
		return data;
	}
	
	
	
}
