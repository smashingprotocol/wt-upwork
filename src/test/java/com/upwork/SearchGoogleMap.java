package com.upwork;


import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.upwork.engine.Config;
import com.upwork.includes.Search;
import com.upwork.request.ClickElement;
import com.upwork.utility.StatusLog;
import com.upwork.utility.TableContainer;
import com.upwork.utility.TakeScreenShot;
import com.upwork.verify.verifyXPath;

public class SearchGoogleMap {

	public String env;
	public String keyword;
	public String title;
	public Boolean NextPageActive;
	
	public boolean testStatus;
	
	
	static Properties sys = System.getProperties();
	
	
	@Test
	public void Search_GoogleMap() throws Exception{

		try 
		{
			
			Config.setup(sys.getProperty("host"));
			env = sys.getProperty("upworkHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			//Long wait = Long.parseLong(pr.getProperty("WAIT_SEC"));
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
	
				Config.driver.navigate().to(sys.getProperty("host"));
				
				keyword = TableContainer.getCellValue(i, "keyword");
				title = TableContainer.getCellValue(i, "title");
				
				Search.keyword(Config.driver,keyword);
				
				//Look until next page is no longer active.
				do {
					Search.storeAllURLinPageResults(Config.driver,sys.getProperty("tableOutPutContainer"));
					NextPageActive = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_BTN_NEXT_ACTIVE_XPATH"));
					
					if(NextPageActive){
						ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_NEXT_ACTIVE_XPATH"));
					}
					
				} while (NextPageActive);

			} //end for	

			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		}
		
			catch (Exception e)
			{
				Assert.fail(e.getMessage());
				
			}
		
	}
	
	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
	}
	
	
}
