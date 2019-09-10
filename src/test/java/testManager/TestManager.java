package testManager;

import apiManager.ApiManager;
import csvManager.CsvManager;
import excelManager.ExcelManager;
import jsonManager.JsonManager;
import logManager.LogManager;
import mysqlManager.MysqlManager;
import org.testng.annotations.Test;
import propertyManager.PropertyManager;

import java.util.List;
import java.util.Map;

public class TestManager extends BeforeTestng {

    private static String excelPath = PropertyManager.getProperty("excelPath");
    private static String csvPath = PropertyManager.getProperty("csvPath");
    private static ExcelManager excelManager = ExcelManager.getInstance(excelPath);
    private static MysqlManager mysqlManager = MysqlManager.getInstance();
    private static JsonManager jsonManager = JsonManager.getInstance();
    private static CsvManager csvManager = CsvManager.getInstance(csvPath);
    private static ApiManager apiManager = ApiManager.getInstance();

    public static void main(String[] args) throws Exception {

        LogManager.setup();
        mysqlManager.mysql();
        excelManager.excelManager();
        jsonManager.jsonManager();
        csvManager.csvManager();
        apiManager.callApi("createCity");
    }

    @Test
    public void testing() throws Exception {

        LogManager.info("Test started");
        driver.get("http://google.com");
        LogManager.debug(driver.getTitle());
        Thread.sleep(3000);
        LogManager.info("Test ended");
    }
}
