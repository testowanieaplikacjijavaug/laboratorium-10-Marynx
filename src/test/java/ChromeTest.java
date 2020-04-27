import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class ChromeTest {
    
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
        driver.get("http://automationpractice.com/index.php");
    }
    
    @Test
    public void testTitle(){
        assertEquals("My Store",driver.getTitle());
    }
    
    @Test
    public void testGoToDresses(){
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]")).click();
        assertEquals("Dresses - My Store",driver.getTitle());
    }
    
    @Test
    public void testSearch(){
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("Skirt" + Keys.ENTER);
        String searchResult=driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1/span[1]")).getText();
        assertEquals("\"SKIRT\"",searchResult);
    }
    
    
    @AfterAll
    public static void tearDown() {
        driver.close();
        driver.quit();
    }
    
}
