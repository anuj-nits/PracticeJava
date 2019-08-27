package extentReport;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import logger.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Date;

public class ExtentReporter {

    // Declaration of the extent report reference variable
    private static ExtentReports extent;
    private static String extentReportPath = System.getProperty("user.dir") + "/src/test/resources/report/";

    // Initialize extent report logger
    private static ExtentTest test;

    private static WebDriver driver;

    // To prevent other classes to create object of this utility class
    private ExtentReporter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Moves old reports to archive folder. Initialize extent report
     */
    public static void setup(WebDriver driver) {

        extent = getInstance(driver);
        Log.info("ExtentReporter setup done");
    }

    /**
     * Starts reporting and display current test under execution in console
     *
     * @param name Name of the test under execution
     * @author anujg
     */
    public static void startTest(String name) {

        test = extent.startTest(name);

        // Display current test under execution in console
        Log.info("#######  " + name + "  #######");
    }

    /**
     * Ends reporting and appends data into extent report
     *
     * @author anujg
     */
    public static void endTest() {

        extent.endTest(test);

        // Writes everything to document
        extent.flush();
    }

    /**
     * Initialize extent report reference variable
     *
     * @return Object of Extent Report
     * @author anujg
     */
    private static ExtentReports getInstance(WebDriver driver) {

        try {
            ExtentReporter.driver = driver;
            // Creates extent report if not done already
            if (extent == null) {
                extent = new ExtentReports(extentReportPath + "index.html", true, DisplayOrder.OLDEST_FIRST);
                test = new ExtentTest("", "");
            }
            return extent;

        } catch (Exception e) {
            Log.error("Error in ExtentReports/getInstance");
            return null;
        }
    }

    /**
     * Capture logs for passed scenarios
     *
     * @param description Logs for passed test
     * @author anujg
     */
    public static void pass(String description) {

        try {
            test.log(LogStatus.PASS, description);
            //captureScreenshot();
            Log.info(description);
        } catch (Exception e) {
            Log.error("Error in ExtentReporter.PASS");
        }
    }

    /**
     * Prints error message, type of error & priority of error along with the screenshot in the report based on type of exception
     *
     * @param description Error message to be shown in reports
     * @author anujg
     */
    public static void fail(String description) {

        try {
            test.log(LogStatus.FAIL, description);
            captureScreenshot();
            Log.error(description);

        } catch (Exception e) {
            Log.error("Issue in ExtentReporter.error method");
        }
    }

    /**
     * Captures screenshot and stores where reports are being generated
     *
     * @author anujg
     */
    private static void captureScreenshot() throws Exception {

        // Stores current date
        Date date = new Date();

        // Appends timeStamp in screenshot's name
        String screenshotName = date.toString().replace(":", "_").replace(" ", "_") + ".png";

        //String screenshotPath = "./src/test/resources/reports/";
        String screenshotPath = extentReportPath;

        // Captures screenshot and stores in a file.
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Stores the captured screenshot in given location
        FileUtils.copyFile(file, new File(screenshotPath + screenshotName));

        // Append screenshot to extent report
        //test.log(LogStatus.INFO, test.addScreenCapture(screenshotName));
        Log.info("Screenshot captured");
    }
}
