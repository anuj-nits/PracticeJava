import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class launch {

    public static void main(String[] args) {

        System.out.println("hello world");
    }

    @Test
    public void testing() throws Exception {

        System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://google.com");
        Thread.sleep(3000);
        driver.quit();
    }
}
