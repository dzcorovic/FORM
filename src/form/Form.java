package form;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

/* 
Automate following Test Steps:
1.	Open http://demoqa.com/automation-practice-form
2.	Enter FistName, LastName, Email
3.	Verify the number of radio buttons  is equal to 3 
4.	Verify that by default none of radio button is selected
5.	Select Female as gender
6.	Enter phone Number
7.	Enter Date Of Birth
8.	Fill in Subject - Science
//use ArrorKeyDown to select 4th element in  autocomplete list when user enters "m" in subject input
9.	Verify that number of checkboxes is 3 
10.	Verify that all checkbox are unchecked
11.	Check Sport and Music 
12.	Upload file
13.	Enter Current Address ZADACA
14.	Verify that select city is disabled 
15.	Verify  the content of State dropdown
16.	Select the state NCR and Gurgaon
17.	Click on Submit button
18.	Get student name from modal 
19.	Close modal 

*/

public class Form {
     
	private WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
	
		Form frm = new Form();
		frm.setUp();
        frm.enterFirstLastEmail("firstName", "Dzenana");
        frm.enterFirstLastEmail("lastName", "Corovic");
        frm.enterFirstLastEmail("userEmail", "dcorovic@gmail.com");
        
        Assert.assertEquals(frm.numOfRadioButton(), 3, "Number of radio buttons is not 3.");
        Assert.assertFalse(frm.isRadioSelected(), "Radio button is selected.");
        
        frm.selectFemale("Female");
        frm.phoneNumber("userNumber", "0611234567");
        frm.dateOfBirth("19 April 1965");
        //frm.selSubject("subjectsInput", "Computer Science");
        frm.subjectM();
        
        Assert.assertEquals(frm.numOfCheckBoxes(), 3, "Number of check boxes is not 3.");
        Assert.assertFalse(frm.boxesUnchecked(), "All check boxes are not unchecked");
        
        frm.selSaM("Sports");
        frm.selSaM("Music");
        
        String filePath= System.getProperty("user.dir") + "\\summer.jpg";
        frm.uploadPhoto(filePath);
        frm.currentAdress("currentAddress", "dcorovic@gmail.com");
        
        //Assert.assertFalse(frm.selCityIsDisabled(), "Select city is enabled.");
        
        //frm.selStateAndCity("NCR");
        
        frm.clickSubButton("submit");
        System.out.println(frm.getStudentName("Dzenana Corovic")); 
        
        frm.closeModal("closeLargeModal");
	}        
	
	public void setUp() {  
		
		String dir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", dir + "\\executable\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://demoqa.com/automation-practice-form");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
	
	public void enterFirstLastEmail(String elementId, String value) {  
		
		driver.findElement(By.id(elementId)).sendKeys(value);
		
	}
	
	public int numOfRadioButton () { 
		
		List<WebElement> radioList = driver.findElements(By.xpath("//input[@name=\"gender\"]"));
		
		return radioList.size();
	}
	
	public boolean isRadioSelected() {  
		
		List<WebElement> radioList = driver.findElements(By.xpath("//input[@name=\"gender\"]"));
		
		for (int i =0; i<radioList.size(); i++) {  
			if(radioList.get(i).isSelected()) { 
				return true; 
			}	
		}
		return false;
	}
	
	public void selectFemale (String element) { 
		
		driver.findElement(By.xpath("//label[@class=\"custom-control-label\"][contains(.,\""+ element +"\")]")).click();
				
	}
	
	public void phoneNumber(String elementId, String num) { 
		
		driver.findElement(By.id(elementId)).sendKeys(num);
	}
	
	public void dateOfBirth(String dateOfBirth) {  
		
		driver.findElement(By.id("dateOfBirthInput")).click();
		String [] date = dateOfBirth.split(" ");
		String day = date[0];
		String month = date[1];
		String year = date [2];
		
		Select selMonth = new Select(driver.findElement(By.className("react-datepicker__month-select")));
		selMonth.selectByVisibleText(month);
		
		Select selYear = new Select(driver.findElement(By.className("react-datepicker__year-select")));
		selYear.selectByValue(year);
		
		List<WebElement> activeDay =  driver.findElements(By.xpath("//div[starts-with(@class, \"react-datepicker__day react-datepicker__day\")][not(contains(@class, \"react-datepicker__day--outside-month\"))]")); 
        
		for (int i=0; i<activeDay.size(); i++) {  
			if(activeDay.get(i).getText().contentEquals(day)) {  
				activeDay.get(i).click();
				break;
			}
		}
	
	}
	
	/*
	public void selSubject(String elementId, String value) { 
		
		driver.findElement(By.id(elementId)).sendKeys(value);
		driver.findElement(By.id(elementId)).sendKeys(Keys.ENTER);
		
	}
	*/
	public void subjectM() throws InterruptedException {   
		
		 Thread.sleep(2000);
		 driver.findElement(By.id("subjectsInput")).sendKeys("m");
		 for (int i=0; i<3; i++) { 
		 driver.findElement(By.id("subjectsInput")).sendKeys(Keys.ARROW_DOWN);
		 Thread.sleep(2000);
		 }
		 driver.findElement(By.id("subjectsInput")).sendKeys(Keys.ENTER);
		 
		 
	}
	
	public int numOfCheckBoxes() { 
		
		List<WebElement> cboxes = driver.findElements(By.xpath("//input[@type=\"checkbox\"]"));
		return cboxes.size();
		
	}
	
	public boolean boxesUnchecked() { 
		
		List<WebElement> cboxes = driver.findElements(By.xpath("//input[@type=\"checkbox\"]"));
		for(int i=0; i<cboxes.size(); i++) { 
			if (cboxes.get(i).isSelected()) { 
				return true;
			}
		}
		return false;
		
	}
	
	public void selSaM(String value) {
		
		driver.findElement(By.xpath("//label[@class=\"custom-control-label\"][contains(.,\""+value+"\")]")).click(); 
		
	}
	
	public void uploadPhoto(String filePath) {  
		
		driver.findElement(By.id("uploadPicture")).sendKeys(filePath);
		
	}
	
	public void currentAdress(String elementId, String value) {   
		
		driver.findElement(By.id(elementId)).sendKeys(value);
		
	}
	
	/*public boolean selCityIsDisabled() {   
		
		System.out.println(driver.findElement(By.id("city")).isEnabled());
		
		if(driver.findElement(By.id("city")).isEnabled()) { 
			
			return true;
		}
		
		return false;
	} */
	
	/*public void selStateAndCity(String element1) { 
		
		driver.findElement(By.id("state")).click();
		
	}
	*/
	
	public void clickSubButton(String elementId) { 
		
		WebElement sub = driver.findElement(By.id(elementId));
		String script = "arguments[0].scrollIntoView()";
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript(script, sub);
		sub.click();
		
	}
	
	public String getStudentName(String value) {  
		
		return driver.findElement(By.xpath("//tr[contains(., \""+ value +"\")]")).getText();
	
	}
	
	public void closeModal(String elementId) throws InterruptedException {  
		
		Thread.sleep(2000);
		driver.findElement(By.id(elementId)).click();
		
	}
	

}
