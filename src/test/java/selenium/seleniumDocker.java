package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.URL;

public class seleniumDocker {

    @Test
    public void runTestOnDocker() throws Exception {

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        URL url = new URL("http://localhost:4444/wd/hub");
        WebDriver driver = new RemoteWebDriver(url, cap);
        driver.manage().window().maximize();
        driver.get("http://google.com");
        System.err.println(driver.getTitle());
        driver.quit();
    }
}
