package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class launch {

    public static void main(String[] args) {

        System.out.println("hello world");
    }

    @Test
    public void testing() throws Exception {

        System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("http://google.com");
        System.err.println(driver.getTitle());
        Thread.sleep(3000);
        driver.quit();
    }
}
