package selenium;

import logger.Log;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BeforeTestng {


    @BeforeSuite
    public void beforeSuite() {

        Log.setup();
    }

    @AfterSuite
    public void afterSuite() {

    }
}
