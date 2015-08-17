package com.upwork;


import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.upwork.engine.Config;
import com.upwork.includes.SignIn;
import com.upwork.utility.StatusLog;
import com.upwork.utility.TableContainer;
import com.upwork.utility.TakeScreenShot;

public class HomepageLogin {

	public String env;
	public String username;
	public String password;
	public String title;
	public boolean testStatus;
	
	
	static Properties sys = System.getProperties();
	
	
	@Test
	public void Homepage_Login() throws Exception{

		try 
		{
			
			Config.setup(sys.getProperty("host"));
			env = sys.getProperty("opstrackHost");
			//Properties pr = Config.properties(); //create a method for the pcm.properies
			
			//Long wait = Long.parseLong(pr.getProperty("WAIT_SEC"));
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
	
					Config.driver.navigate().to(sys.getProperty("host"));
					
					username = TableContainer.getCellValue(i, "username");
					password = TableContainer.getCellValue(i, "password");
					title = TableContainer.getCellValue(i, "title");
					
					SignIn.login(Config.driver, username, password);
					

			} //end for	

			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		}
		
			catch (Exception e)
			{
				Assert.fail("[LOGIN] An Error encountered on login: " + e.getMessage());
				
			}
		
	}
	
	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
	}
	
	
}
