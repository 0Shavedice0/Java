import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest {
	private static WebDriver driver = null;
	
	public static void main(String[] args) throws InterruptedException {
		String Local ="D:\\program\\chromedriver.exe";
		
		
		String url="https://www.cmoney.tw/finance/f00040.aspx?s=3264";
		System.setProperty("webdriver.chrome.driver",Local);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1400,800");
		options.addArguments("headless");
		
		WebDriver driver = new ChromeDriver(options);
		
		
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  System.out.println("Start");
		  driver.get(url);
		  String A;
		  Thread.sleep(1000);
		  for (int i = 2;i<=5;i++) {
			  
		  }
		   A = driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody")).getText();
		   driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[2]/a")).click();
		   //System.out.println(A+"\n");
		   Thread.sleep(1000);
		   
		   A = driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody")).getText();
		   Thread.sleep(1000);
		  // System.out.println(A+"\n");
		   driver.findElement(By.xpath(" //*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[3]/a")).click();
		   
		   //System.out.println("\n\n");
		   List<?>  col = driver.findElements(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody/tr/th"));
	        System.out.println("Total No of columns are : " +col.size());
	        
	        List<WebElement>  rows = driver.findElements(By.xpath ("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody/tr"));
	        
	        
	        for(WebElement i :rows) {
	        	System.out.println("Total No of rows are : " + rows.size());	
	        }
	        A="\""+A+"\"";
	        A=A.replaceAll(" ","\",\"");
	      //  System.out.println(A.replaceAll("\r\n", "\"\r\n")+"\n");
	      //  System.out.println(A);
		  driver.quit(); 
		 }
	}