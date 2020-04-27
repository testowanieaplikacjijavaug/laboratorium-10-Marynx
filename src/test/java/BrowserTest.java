import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.*;

import java.util.List;

import java.util.concurrent.TimeUnit;

public class BrowserTest {
    
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
        driver.get("https://www.duckduckgo.com");
    }
    
    //inna metoda niz click()
    @Test
    public void testPressEnter(){
        driver.findElement(By.cssSelector("#search_form_input_homepage")).sendKeys("Selenium" + Keys.ENTER);
        assertEquals("Selenium at DuckDuckGo", driver.getTitle());
        
    }
    
    @Test
    public void testSubmit(){
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Selenium");
        driver.findElement(By.id("search_button_homepage")).submit();
        assertEquals("Selenium at DuckDuckGo", driver.getTitle());
    }
    
    //pierwszy i trzeci element
    @Test
    public void testGetFirstResult(){
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Selenium"+Keys.ENTER);
        driver.findElement(By.id("r1-2")).click();
        assertNotNull(driver.getTitle());
    }
    
    @Test
    public void testThirdResult() {
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Selenium"+Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"r1-2\"]/div/h2/a[1]")).click();
        assertNotNull(driver.getTitle());
    }
    
    //gdy nie znajdziemy szukanego elementu
    @Test
    public void testNotExistingElement() {
        try {
            driver.findElement(By.xpath("wqewrqwerwerwre"));
            fail();
        } catch ( NoSuchElementException e ) {
            assertTrue(true);
        }
    }
}
