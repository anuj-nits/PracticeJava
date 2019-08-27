package selenium;

import database.Mysql;
import excelManager.ExcelManager;
import jsonManager.JsonManager;
import logger.Log;
import org.openqa.selenium.json.Json;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class Launch extends BeforeTestng {

    public static void main(String[] args) throws Exception {

        mysql();
        excelManager();
        jsonManager();
    }

    private static void mysql() throws Exception {

        String tableName = "Authors";
        String nameColumn = "Name";
        String idColumn = "Id";
        String idColumnValue = "1";
        String nameColumnValue = "Jack London";
        String updatedName = "Jakson London";

        Mysql.createConnection();
        System.out.println(Mysql.getRowCount(tableName));
        System.err.println(Mysql.getColumnCount(tableName));
        System.out.println(Mysql.getPartialRowCount(tableName, nameColumn, nameColumnValue));
        System.err.println(Mysql.getCellData(tableName, nameColumn, idColumn, idColumnValue));

        Object[][] ooo = Mysql.tableToObject(tableName);
        for (Object[] oo : ooo)
            for (Object o : oo)
                System.out.println(o.toString());

        Map<String, String> m = Mysql.getRowData(tableName, idColumn, idColumnValue);
        for (Map.Entry<String, String> e : m.entrySet())
            System.err.println(e.getKey() + ": " + e.getValue());

        List<Map<String, String>> l = Mysql.getMultipleRowData(tableName, idColumn, idColumnValue);

        for (Map<String, String> ll : l)
            for (Map.Entry<String, String> e : ll.entrySet())
                System.out.println(e.getKey() + ": " + e.getValue());

        List<Map<String, String>> lll = Mysql.getTableData(tableName);
        for (Map<String, String> ll : lll)
            for (Map.Entry<String, String> e : ll.entrySet())
                System.err.println(e.getKey() + ": " + e.getValue());

        Mysql.updateCellData(tableName, idColumn, idColumnValue, nameColumn, updatedName);
        Mysql.closeConnection();
    }

    private static void excelManager() throws Exception {

        String sheetName = "Sheet1";
        int rowNumber = 1;

        System.out.println(ExcelManager.getRowAsMap(sheetName, rowNumber));
        System.err.println(ExcelManager.getColumnsAsMap(sheetName));
    }

    private static void jsonManager() throws Exception {

        System.out.println(JsonManager.readFromJson("id"));
        JsonManager.writeToJson("name", "Anuj Gupta");
        System.out.println(JsonManager.readFromJson("name"));
        JsonManager.writeToJson("id", "1");
    }

    @Test
    public void testing() throws Exception {

        Log.info("Test started");
        driver.get("http://google.com");
        Log.debug(driver.getTitle());
        Thread.sleep(3000);
        Log.info("Test ended");
    }
}
