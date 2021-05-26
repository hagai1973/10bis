package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.xml.LaunchSuite.ExistingSuite;

public class Login_cucumber extends Base {

	public Login_cucumber(WebDriver driver) {
		super(driver);
	}

	// start login
	public boolean doLoginFacebook(String user, String password, String name) throws InterruptedException {

		String baseHandle = driver.getWindowHandle();
		
		//Click facebook image
		click(By.xpath("//img[@type='facebook']"));
		
		//switch to Facebook login window
		
		Thread.sleep(2000);
		Set<String> handels = driver.getWindowHandles();

		for (String h : handels) {
			if (!h.equals(baseHandle))
				driver.switchTo().window(h);
		}

		//type user/password
		//driver.findElement(By.id("email")).sendKeys(user);
		//Thread.sleep(1000);

		//driver.findElement(By.id("pass")).sendKeys(password);
		Thread.sleep(2000);
		click(By.name("login"));

		Thread.sleep(3000);
//		if (driver!=null)
//			driver.close();
		
		driver.switchTo().window(baseHandle);

		click(By.xpath("//img[@type='facebook']"));
		Thread.sleep(4000);
		return true;

		
//		String personalMsg = getText(By.cssSelector(".UserStatus__UserTextWrapper-sueilz-2"));
//		
//		if (personalMsg.contains(name))
//			return true;
//		else
//			return false;

	}
	
	// start login
		public boolean verifyLoginFacebook(String name) throws InterruptedException {

			
			
			String personalMsg = getText(By.cssSelector(".UserStatus__UserTextWrapper-sueilz-2"));
			
			if (personalMsg.contains(name))
				return true;
			else
				return false;

		}

	
	public boolean verifyErrMsg() throws InterruptedException {
		click(By.xpath("//button[@data-test='login-submit']"));
		
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();
		
		
		
		Thread.sleep(1000);
		
		String err = driver.findElement(By.xpath("//div[@role='alert']")).getText();
		
		if (err.equals("שדה חובה"))
			return true;
		else
			return false;
	}

}