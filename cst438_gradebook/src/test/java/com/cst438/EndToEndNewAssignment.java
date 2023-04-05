package com.cst438;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@SpringBootTest
public class EndToEndNewAssignment {
	
	public static final String CHROME_DRIVER_LOCATION = "/Users/saul/Downloads/chromedriver_mac64/chromedriver";

	public static final int SLEEP_DURATION = 1000;
	
	public static final String URL = "http://localhost:3000";
	
	public static final int ID = 999001;
	
	public static final String ASSIGNMENT_NAME = "TEST LAB 1";
	
	public static final String ASSIGNMENT_DATE = "2020-04-01";
	
	@Test
	public void newAssignmentTest() throws Exception{
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);
		
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");	


        WebDriver driver = new ChromeDriver(ops);

        try {
        	WebElement web_element;
        	
        	driver.get(URL);
        	Thread.sleep(SLEEP_DURATION);
        	
        	web_element = driver.findElement(By.id("addassignment"));
        	web_element.click();
        	
        	web_element = driver.findElement(By.name("id"));
        	web_element.sendKeys(Integer.toString(ID));
        	
        	web_element = driver.findElement(By.name("name"));
        	web_element.sendKeys(ASSIGNMENT_NAME);
        	
        	web_element = driver.findElement(By.name("due"));
        	web_element.sendKeys(ASSIGNMENT_DATE);
        	
        	
        	web_element = driver.findElement(By.id("Submit"));
        	web_element.click();
        	Thread.sleep(SLEEP_DURATION);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	throw e;
        	
		} finally {
			driver.close();
			driver.quit();

		}
	}
}
