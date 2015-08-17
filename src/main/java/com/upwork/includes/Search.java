package com.upwork.includes;

import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.upwork.engine.Config;
import com.upwork.form.SetInputField;
import com.upwork.request.ClickElement;
import com.upwork.utility.TableContainer;
import com.upwork.utility.Wait;
import com.upwork.verify.verifyXPath;

public class Search {
	
	public static Properties properties;
	
	
	public static void keyword(WebDriver driver, String keyword) throws Exception {
		
		FileReader reader = new FileReader("upwork.properties");
		properties = new Properties();
		properties.load(reader);
		
		SetInputField.byXPath(driver, properties.getProperty("SEARCH_INPUT_SEARCH_XP"), keyword);
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_SEARCH_XP"));
		Wait.waitforXPath(driver, properties.getProperty("SEARCH_LINKITEM_NOURL_XPATH"), "10");
		
		
	}

	public static int getItemListCount(WebDriver driver) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void storeAllURLinPageResults(WebDriver driver, String excelFileDir) throws Exception {
		
		FileReader reader = new FileReader("upwork.properties");
		properties = new Properties();
		properties.load(reader);
		
		int resultCtr = verifyXPath.count(Config.driver, properties.getProperty("SEARCH_LINKITEM_NOURL_XPATH"));
		String CompanyName;
		int RowLastNum;
		
		System.out.println(resultCtr);
		
		//get the last row count first
		RowLastNum = TableContainer.getRowCountinExcelFile(excelFileDir);
		
		for(int n = 1; n <= resultCtr; n++){
			
			try {
				
				String curXPath = "(" + properties.getProperty("SEARCH_LINKITEM_NOURL_XPATH") + ") [position()=" + n + "]";
				CompanyName = verifyXPath.getText(Config.driver, curXPath);
				Boolean xpathFound = verifyXPath.isfound(Config.driver, curXPath);
				
				//click each results on the page.
				if(xpathFound){
					ClickElement.byXPath(Config.driver, curXPath);
					
					String URl = verifyXPath.getText(Config.driver, properties.getProperty("ITEM_LINKSPAN_URL_XPATH"));
					
					//insert after the last row
					int rowIndex = RowLastNum + n;

					TableContainer.insertIntoCellValue(excelFileDir, rowIndex, "companyName",CompanyName);
					TableContainer.insertIntoCellValue(excelFileDir, rowIndex, "url",URl);
					
					ClickElement.byXPath(Config.driver, properties.getProperty("SEARCH_LINK_LISTALLRESULTS_XPATH"));
					
				} //end if
				
				
			} catch (Throwable e){
				
			}
			
			
		} //end for
		
		
	}

}
