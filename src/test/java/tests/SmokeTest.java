package tests;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.Login;
import pages.Login_cucumber;
import pages.Main;
import utilites.GetDriver;
import utilites.Utilities;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;


public class SmokeTest {

	// Global variables 
	// Add extent reports
	private ExtentReports extent;
	private ExtentTest myTest;
	private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\reportSanity.html";

	private WebDriver driver;

	//pages
	private Main main;
	private Login_cucumber login;
	
	private static final Logger logger = LogManager.getLogger(SmokeTest.class);
	private static String userName;
	private static String password;
	private static String browser;
	private static String baseUrl;
	private static String user;
	private static String hebresName;
	

	

	@BeforeClass
	public void beforeClass() throws ParserConfigurationException, SAXException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\extent-config.xml"));
		
		baseUrl = Utilities.getDataFromXML("info.xml", "website", 0);
		browser = Utilities.getDataFromXML("info.xml", "browser", 0);
		userName = Utilities.getDataFromXML("info.xml", "userName", 0);
		password = Utilities.getDataFromXML("info.xml", "password", 0);
		user = Utilities.getDataFromXML("info.xml", "user", 0);
		hebresName = Utilities.getDataFromXML("info.xml", "Hebrewname", 0);
		
		//driver = GetDriver.getDriver(browser, baseUrl, user);
		
		main = new Main(driver);
		login = new Login_cucumber(driver);

	}

	
	
	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		myTest = extent.startTest(method.getName());
		myTest.log(LogStatus.INFO, "Starting test", "Start test");
	}
		
	/*  Prerequisite: getting into https://www.10bis.co.il/
	 * 		Given: Client click connection and  
	 * 		When: give Facebook login details and click login
	 *  	Then: Getting into 10bis as registered user
	 */
	
	
	
	@Given("open Google Chrome and go to 10bis")
	@Test(priority = 1, enabled = true, description = "Go to login")
	public void LoginUsingFacebook_1() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		logger.info("Going to connection page");
		driver = GetDriver.getDriver("chrome", "https://10bis.co.il", "hagai");

		main = new Main(driver);

		main.login();
		
	}
	
	@When("I click on login and login with Facebook account")	
	@Test(priority = 2, enabled = true, description = "Login 10bis using Facebook")
	public void LoginUsingFacebook_2() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		login = new Login_cucumber(driver);

		logger.info("Going to login with facebook detailse");
		Assert.assertTrue(login.doLoginFacebook("", "", "חגי"), "could not login with Facebook account, check logs");
		
		logger.info("Successfully Get into 10bis as registred user page (facebook login)");

	}
	
	@Then("user should be able to login successfully")	
	@Test(priority = 3, enabled = true, description = "Login 10bis using Facebook")
	public void LoginUsingFacebook_3() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		login = new Login_cucumber(driver);

		logger.info("Going to verify login");
		Assert.assertTrue(login.verifyLoginFacebook("חגי"), "could not login with Facebook account, check logs");
		
		logger.info("Successfully Get into 10bis as registred user page (facebook login)");
		driver.quit();
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			myTest.log(LogStatus.FAIL, "Test failed: " + result.getName());
			myTest.log(LogStatus.FAIL, "Test failed reason: " + result.getThrowable());
			myTest.log(LogStatus.FAIL, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));
		}
		else {
			myTest.log(LogStatus.PASS, result.getName(), "Verify successful ");
			myTest.log(LogStatus.PASS, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));

		}

		myTest.log(LogStatus.INFO, "Finish test", "Finish test ");
		extent.endTest(myTest);
	
		//return to base URL 
		//driver.get(baseUrl);
	}

	@AfterClass
	public void afterClass() {
		extent.flush();
		extent.close();
		driver.quit();

	}
	
}
