package draganddrop;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DragAndDrop {

	/*
	Test Case:
	1. go to http://demoqa.com/automation-practice-form
	2. click on Interactions
	3. click on Droppable menu item
	4. drag element to drop section
	5. go to http://demo.guru99.com/test/simple_context_menu.html
	6. right click on "right click me" button and click on "cut" option
	 * 
	 */
	private WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
	
		DragAndDrop dad = new DragAndDrop();
		dad.setUp();
		dad.clickInteractions("Interactions");
		dad.clickDropable("Droppable");
		dad.dragElement();
		dad.guruClick();

	}
    
	public void setUp() { 
	
		String dir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", dir + "\\executable\\chromedriver.exe");
		driver= new ChromeDriver();
		driver.get("http://demoqa.com/automation-practice-form");
		driver.manage().window().maximize();
		
	}
	
	public void clickInteractions(String menuItem) throws InterruptedException {  
		
		WebElement sub = driver.findElement(By.xpath("//div[@class =\"header-text\"][contains(.,\""+ menuItem+"\")]"));
		String script = "arguments[0].scrollIntoView()";
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript(script, sub);
		sub.click();
		Thread.sleep(2000);
	}
	
	public void clickDropable(String subItem) {  
		
		WebElement sub = driver.findElement(By.xpath("//ul[contains(@class, \"menu-list\")]//li[contains(., \""+subItem+"\")]"));
		String script = "arguments[0].scrollIntoView()";
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript(script, sub);
		sub.click();
		
	}
	
	public void dragElement() { 
		
		Actions action = new Actions(driver);
		WebElement sourceElement = driver.findElement(By.id("draggable"));
		WebElement targetElement = driver.findElement(By.id("droppable"));
		action.dragAndDrop(sourceElement, targetElement).perform();
		
	}
	
	public void guruClick() { 
		
		driver.get("http://demo.guru99.com/test/simple_context_menu.html");
		
		WebElement button = driver.findElement(By.cssSelector(".context-menu-one.btn.btn-neutral"));
        WebElement cut = driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon-cut")); 
        Actions action = new Actions(driver);
        action.contextClick(button).click(cut).perform();
        
	}
}
