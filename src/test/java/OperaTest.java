import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class OperaTest {
    
    private static WebDriver driver;
    
    @BeforeAll
    public static void setUpDriver(){
        System.setProperty("webdriver.opera.driver", "resources/operadriver.exe");
        OperaOptions operaOptions = new OperaOptions();
        operaOptions.addArguments("--headless");
        driver = new OperaDriver(operaOptions);
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
    
}
