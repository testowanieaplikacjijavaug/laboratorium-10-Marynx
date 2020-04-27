import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class LoginTesting {
    
    private static WebDriver driver;
    
    @BeforeAll
    public static void setUpDriver(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @BeforeEach
    public void setUp(){
        driver.manage().deleteAllCookies();
        driver.get("https://www.phptravels.net/login");
    }
    
    @Test
    public void testLogInWithInvalidData(){
        driver.findElement(By.cssSelector("#loginfrm > div.wow.fadeIn > div:nth-child(1) > label > input[type=email]")).sendKeys("Bad");
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[3]/div[2]/label/input")).sendKeys("asda");
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/button")).click();
        String message= driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[1]/div")).getText();
        assertEquals("Invalid Email or Password",message);
    }
    
    @Test
    public void testLogInWithoutPassword(){
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[3]/div[1]/label/input")).sendKeys("Bad");
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/button")).click();
        String message= driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[1]/div")).getText();
        assertEquals("Invalid Email or Password",message);
    }
    
    @Test
    public void testLogInWithoutEmail(){
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[3]/div[2]/label/input")).sendKeys("asda");
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/button")).click();
        String message= driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[1]/div")).getText();
        assertEquals("Invalid Email or Password",message);
    }
    
    
    @Test
    public void testLogInWithValidData(){
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[3]/div[1]/label/input")).sendKeys("user@phptravels.com");
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[3]/div[2]/label/input")).sendKeys("demouser");
        driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"bookings\"]/div[2]/div[4]/a")));
        assertEquals("My Account",driver.getTitle());
    }
    
    
    @AfterAll
    public static void tearDown() {
        driver.get("https://www.phptravels.net/account/logout");
        driver.close();
    }
}
