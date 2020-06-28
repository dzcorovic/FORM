package alert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/*
 * TEST CASE:
1. go to http://demoqa.com/automation-practice-form
2. click on Alerts, Frames & Windows menu
3. click on Alerts menu item
4. click on First "click me button", get alert text and accept alert
5. click on Second "click me button", get alert text and accept alert
6. click on Third "click me button", dismiss alert, get confirmation text
7. click on Fourth "click me button", sent text to alert, click on OK button in alert,  get confirmation text on the page
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertTest {
	
    private WebDriver driver;
    
	public static void main(String[] args) throws InterruptedException {
		AlertTest alert = new AlertTest();
		alert.setUp();
		alert.clickOnAlertsFramesWindows("Alerts, Frame & Windows");
		alert.clickOnAlertsMenuItem("Alerts");
        alert.clickFirstButton("alertButton");
        alert.clickSecondButton("timerAlertButton");
        alert.clickThridButton("confirmButton");
        alert.clickFourthButton("promtButton");
	}
	
	public void setUp() { 
		
		String dir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", dir + "\\executable\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("http://demoqa.com/automation-practice-form");
		
	}
   
	public void clickOnAlertsFramesWindows(String menuItem) { 
		
		WebElement sub = driver.findElement(By.xpath("//div[@class=\"header-text\"][contains(., \""+menuItem +"\")]"));
		String script = "arguments[0].scrollIntoView()";
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript(script, sub);
		sub.click();
		
	}
	
	public void clickOnAlertsMenuItem(String subItem) { 
		
		WebElement sub = driver.findElement(By.xpath("//ul[contains(@class,\"menu-list\")]//li[contains(.,\""+subItem+"\")]"));
		
	//	WebElement sub = driver.findElement(By.xpath("//div[contains(@class,\"show\")]//li[contains(.,\""+subItem+"\")]"));  
		String script = "arguments[0].scrollIntoView()";
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript(script, sub);
		sub.click();
		
	}
	
	public void clickFirstButton(String elementId) throws InterruptedException { 
		
		driver.findElement(By.id(elementId)).click();
		String FirstAlert = driver.switchTo().alert().getText();
		System.out.println("This is first alert text " + FirstAlert);
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
	}
	
	public void clickSecondButton(String elementId) throws InterruptedException { 
		
		driver.findElement(By.id(elementId)).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		String SecondAlert =driver.switchTo().alert().getText();
		System.out.println("This is second alert text " + SecondAlert);
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		
	}
	
	public void clickThridButton(String elementId) throws InterruptedException { 
		
		driver.findElement(By.id(elementId)).click();
		Thread.sleep(2000);
		driver.switchTo().alert().dismiss();
		String ThirdAlert = driver.findElement(By.id("confirmResult")).getText();
		System.out.println("Text after dissmis is " + ThirdAlert);
		
	}
	
	public void clickFourthButton(String elementId) throws InterruptedException { 
		
		driver.findElement(By.id(elementId)).click();
		driver.switchTo().alert().sendKeys("Dzenana");
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		String FourthAlert = driver.findElement(By.id("promptResult")).getText();
		System.out.println("Text enter to prompt " + FourthAlert);
		
		
	}
	
}
