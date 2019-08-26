import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.URL;

public class seleniumDocker {

    @Test
    public void runTestOnDocker() throws Exception {

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
        URL url = new URL("http://localhost:32770/wd/hub");
        WebDriver driver = new RemoteWebDriver(url, cap);
        driver.manage().window().maximize();
        driver.get("http://google.com");
        System.out.println(driver.getTitle());
    }
}
