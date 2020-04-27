import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class PsUgTest {
    
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
        driver.get("https://pe.ug.edu.pl");
    }
    
    @Test
    public void testCountAllLinks(){
        int links = driver.findElements(By.xpath(".//a")).size();
        assertEquals(16, links);
        
    }
    
    @Test
    public void testCountAllImgs(){
        int links = driver.findElements(By.xpath(".//img")).size();
        assertEquals(2, links);
    }
    
    @Test
    public void testGoToLinks(){
        List<String> urls = driver.findElements(By.xpath("//a"))
                .stream()
                .map(x -> x.getAttribute("href"))
                .collect(Collectors.toList());
        String startTitle = driver.getTitle();
        for (String url : urls) {
            driver.get(url);
            driver.navigate().back();
        }
    
        assertEquals("Portal Edukacyjny UG", startTitle);
    }
    
    @Test
    public void testInput() {
        driver.get("https://pe.ug.edu.pl/awaria");
        List<WebElement> formElements = driver.findElement(By.xpath("//form"))
                .findElements(By.xpath("./*"));
        assertFalse(formElements.isEmpty());
    }
    
    @AfterAll
    public static void tearDown() {
        driver.close();
    }
}
