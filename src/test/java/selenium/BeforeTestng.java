package selenium;

import LogManager.LogManager;
import extentManager.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BeforeTestng {

    public WebDriver driver;

    @BeforeSuite
    public void beforeSuite() {

        String chromeDriverPath = "./src/test/resources/drivers/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        LogManager.setup();
        ExtentManager.setup(driver);
    }

    @BeforeMethod
    public void beforeTest(ITestResult result) {

        ExtentManager.startTest(result.getMethod().getMethodName());
    }

    @AfterMethod
    public void afterTest(ITestResult result) {

        String methodName = result.getMethod().getMethodName();
        if (result.getStatus() == 1)
            ExtentManager.pass(methodName + " Pass");
        if (result.getStatus() == 2)
            ExtentManager.fail(methodName + "failed");
        ExtentManager.endTest();
    }

    @AfterSuite
    public void afterSuite() {
        driver.quit();
    }
}
