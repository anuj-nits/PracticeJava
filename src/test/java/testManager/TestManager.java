package testManager;

import csvManager.CsvManager;
import excelManager.ExcelManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jsonManager.JsonManager;
import logManager.LogManager;
import mysqlManager.MysqlManager;
import org.testng.annotations.Test;
import propertyManager.PropertyManager;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestManager extends BeforeTestng {

    private String excelPath = PropertyManager.getProperty("excelPath");
    private String csvPath = PropertyManager.getProperty("csvPath");
    private ExcelManager excelManager = ExcelManager.getInstance(excelPath);
    private MysqlManager mysqlManager = MysqlManager.getInstance();
    private JsonManager jsonManager = JsonManager.getInstance();
    private CsvManager csvManager = CsvManager.getInstance(csvPath);

    public static void main(String[] args) throws Exception {

        LogManager.setup();
        TestManager testManager = new TestManager();
        testManager.mysql();
        testManager.excelManager();
        testManager.jsonManager();
        testManager.csvManager();
        testManager.callApi("createCity");
    }

    private void mysql() throws Exception {

        String tableName = "Authors";
        String nameColumn = "Name";
        String idColumn = "Id";
        String idColumnValue = "1";
        String nameColumnValue = "Jack London";
        String updatedName = "Jakson London";

        mysqlManager.createConnection();
        System.out.println(mysqlManager.getRowCount(tableName));
        System.err.println(mysqlManager.getColumnCount(tableName));
        System.out.println(mysqlManager.getPartialRowCount(tableName, nameColumn, nameColumnValue));
        System.err.println(mysqlManager.getCellData(tableName, nameColumn, idColumn, idColumnValue));

        Object[][] ooo = mysqlManager.tableToObject(tableName);
        for (Object[] oo : ooo)
            for (Object o : oo)
                System.out.println(o.toString());

        Map<String, String> m = mysqlManager.getRowData(tableName, idColumn, idColumnValue);
        for (Map.Entry<String, String> e : m.entrySet())
            System.err.println(e.getKey() + ": " + e.getValue());

        List<Map<String, String>> l = mysqlManager.getMultipleRowData(tableName, idColumn, idColumnValue);

        for (Map<String, String> ll : l)
            for (Map.Entry<String, String> e : ll.entrySet())
                System.out.println(e.getKey() + ": " + e.getValue());

        List<Map<String, String>> lll = mysqlManager.getTableData(tableName);
        for (Map<String, String> ll : lll)
            for (Map.Entry<String, String> e : ll.entrySet())
                System.err.println(e.getKey() + ": " + e.getValue());

        mysqlManager.updateCellData(tableName, idColumn, idColumnValue, nameColumn, updatedName);
        mysqlManager.closeConnection();
    }

    private void excelManager() throws Exception {

        String sheetName = "Sheet1";
        int rowNumber = 1;

        System.out.println(excelManager.getRowAsMap(sheetName, rowNumber));
        System.err.println(excelManager.getColumnsAsMap(sheetName));
    }

    private void jsonManager() throws Exception {

        String filePath = PropertyManager.getProperty("jsonPath");
        Map<String, Object> map = jsonManager.readFromJson(filePath);
        jsonManager.replaceJsonVariable(map, "name", "anuj");
        jsonManager.replaceJsonVariable(map, "id", "1");
        jsonManager.replaceJsonVariable(map, "age", "20");
        jsonManager.writeToJson(filePath, map);
    }

    private void csvManager() throws Exception {

        System.out.println(csvManager.readFromCSV(1, 1));
        csvManager.writeToCSV("dandy", 1, 1);
    }

    private void callApi(String apiName) throws Exception {

        String filePath = PropertyManager.getProperty("baseDataPath") + apiName;
        Map<String, String> excelData = excelManager.getRowAsMap(apiName, 1);
        Map<String, Object> jsonMap = jsonManager.readFromJson(filePath + ".json");
        jsonMap.putAll(excelData);
        RestAssured.baseURI = PropertyManager.getProperty("api.baseURI");
        Response response = given().
                when().
                header("username", PropertyManager.getProperty("api.username")).
                header("password", PropertyManager.getProperty("api.password")).
                header("content-type", PropertyManager.getProperty("api.content-type")).
                body(jsonMap).
                post(jsonMap.get("payload").toString());
        System.out.println(response.asString());
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
