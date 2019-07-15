package flipcart;

import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;
//import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;


/*
 * This Class contains login function with supporting methods 
 */

public class loginTest {
	public WebDriver driver;
	utilityclass file_util =new utilityclass();
	@Test //(dataProvider = "dp")
  public void TestCase1()  {
	setup("chrome");  
	FlipCartLogin();
	Select_Product();
	logout();
	//driver.quit();
  }
  @Test(enabled=false)
  public void TestCase2() {
		setup("firefox");  
		FlipCartLogin();
		Select_Product();
		driver.quit();
	 
  }
/*
  @DataProvider	
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 2, "b" },
    };
  }
  */
  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("Before executed");
	  //setup("chrome");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("After Executed");
	  logout();
	  driver.close();
	 // driver.quit();
  }
  
  /* This Method is responsible for web driver object creation 
  * It also require browser name as an parameter to open desired browser 
  * Parameter should be like "chrome/firefox/ie"
  */
  public void setup(String browser)
  {
	  String BrowserName=browser;
	  if (BrowserName.contains("chrome"))
	  {  //Setting property for chrome driver
	  System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
	   driver = new ChromeDriver();
	   driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 driver.get("https://www.flipkart.com");
	  }else if (BrowserName.contains("firefox"))
	  {	  //Setting property for Firefox driver
		 System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
		 driver = new FirefoxDriver();
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 driver.get("https://www.flipkart.com");
	  }else if (BrowserName.contains("ie"))
	  {  //Setting property for IE driver
		 System.setProperty("webdriver.ie.driver", "src/main/resources/IEDriverServer.exe");
		 driver = new InternetExplorerDriver();
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 driver.get("https://www.flipkart.com");
	  }else 
	  {
		  
	  }
  }
  
  
  // Login method for performing Flipcart login operation
  
  public boolean FlipCartLogin()
  {
	  WebDriverWait wait = new WebDriverWait(driver, 15);
	  WebElement LoginName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Enter Email')]//preceding::input[@type='text'][1]")));
	  LoginName.sendKeys("9011099241");
	//  driver.findElement(By.xpath("//span[contains(text(),'Enter Email/Mobile number')]")).sendKeys("ee9011099241");
	  driver.findElement(By.xpath("//input[@type='password']")).sendKeys("9011099241");
	  driver.findElement(By.xpath("//span[contains(text(),'Login')]//following::button")).click();
	
	  // Asserting for successful login
	  if(driver.findElement(By.xpath("//div[contains(text(),'Ankush')]")).isDisplayed())
	  {
		  System.out.println("Login Successful");
		  Reporter.log("Login test passed");
		  Assert.assertTrue(true);
		  return true;
	  }else 
	  {
		  System.out.println("Login Fail");
		  Reporter.log("Login test Fail");
		  Assert.assertTrue(false);
		  return false;
	  }  
	  
	  
  }

  // This function is responsible for selecting product and adding into the cart.
  public boolean Select_Product()
  {
	  boolean bolResult=false;
	  String strFilePath="src/main/resources/data.csv";
	  String ProductName[]=file_util.parsetextdata(strFilePath);
	  
	  
	  
	  for (int i=0;i<=ProductName.length-1;i++)
	  {
		  String[] productclass=ProductName[i].split(",");  
		  
	  driver.findElement(By.xpath("//input[@type='text' and @name='q']")).sendKeys(productclass[1]);
	  driver.findElement(By.xpath("//input[@type='text' and @name='q']")).sendKeys(Keys.RETURN);
	  
	  WebDriverWait wait = new WebDriverWait(driver, 10);
	  WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'"+productclass[0]+"')]")));
	  productName.click();
	  
	  // Switch window 
	  Set<String> winHandles = driver.getWindowHandles();
	  
	  for(String handle: winHandles){
        
		  if (driver.getTitle().contains(productclass[0]))
		  {
			  System.out.println("Title of the new window: " +  driver.getTitle()); 
			  break;
		  }
          driver.switchTo().window(handle);
          
      }
	  
	 // Click on add to care button
	 WebElement AddtoCart1= driver.findElement(By.xpath("//*[@class='col col-6-12']/button"));
	 highLighterMethod(driver,AddtoCart1);
	 WebElement AddtoCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='col col-6-12']/button")));
	 AddtoCart.click();
	 
	 if(driver.findElement(By.xpath("//a[contains(text(),'"+productclass[0]+"')]")).isDisplayed())
	  {
		  System.out.println("Product successfuliy added to cart "+productclass[0]);
		  Reporter.log("Product successfuliy added to cart " +productclass[0]);
		  Assert.assertTrue(true);
		  bolResult= true;
	  }else 
	  {
		  System.out.println("Failed  to add product in to cart "+productclass[0]);
		  Reporter.log("Failed  to add product in to cart "+productclass[0]);
		  Assert.assertTrue(false);
		  bolResult= false;
	  }  
	 
	 
	  }
	 
	 
	  return bolResult;
	
	  
  }
  
  public void logout()
  {
	// Clicking on logout
		 WebElement usermenu = driver.findElement(By.xpath("//div[contains(text(),'Ankush')]"));
		 Actions action = new Actions(driver);
		 action.moveToElement(usermenu).clickAndHold().build().perform();
		 
		// driver.findElement(By.xpath("//div[contains(text(),'Ankush')]")).click();
		 driver.findElement(By.xpath("//a[@href='#']")).click(); 
  }
  
  // This is utility method for highlighting the object in DOM
 
  public void highLighterMethod(WebDriver driver, WebElement element){
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
	  }
  
  
  
  
}
