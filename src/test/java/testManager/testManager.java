package testManager;

import LogManager.LogManager;
import csvManager.CsvManager;
import mysqlManager.MysqlManager;
import excelManager.ExcelManager;
import jsonManager.JsonManager;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class testManager extends BeforeTestng {

    public static void main(String[] args) throws Exception {

        mysql();
        excelManager();
        jsonManager();
        csvManager();
    }

    private static void mysql() throws Exception {

        String tableName = "Authors";
        String nameColumn = "Name";
        String idColumn = "Id";
        String idColumnValue = "1";
        String nameColumnValue = "Jack London";
        String updatedName = "Jakson London";

        MysqlManager.createConnection();
        System.out.println(MysqlManager.getRowCount(tableName));
        System.err.println(MysqlManager.getColumnCount(tableName));
        System.out.println(MysqlManager.getPartialRowCount(tableName, nameColumn, nameColumnValue));
        System.err.println(MysqlManager.getCellData(tableName, nameColumn, idColumn, idColumnValue));

        Object[][] ooo = MysqlManager.tableToObject(tableName);
        for (Object[] oo : ooo)
            for (Object o : oo)
                System.out.println(o.toString());

        Map<String, String> m = MysqlManager.getRowData(tableName, idColumn, idColumnValue);
        for (Map.Entry<String, String> e : m.entrySet())
            System.err.println(e.getKey() + ": " + e.getValue());

        List<Map<String, String>> l = MysqlManager.getMultipleRowData(tableName, idColumn, idColumnValue);

        for (Map<String, String> ll : l)
            for (Map.Entry<String, String> e : ll.entrySet())
                System.out.println(e.getKey() + ": " + e.getValue());

        List<Map<String, String>> lll = MysqlManager.getTableData(tableName);
        for (Map<String, String> ll : lll)
            for (Map.Entry<String, String> e : ll.entrySet())
                System.err.println(e.getKey() + ": " + e.getValue());

        MysqlManager.updateCellData(tableName, idColumn, idColumnValue, nameColumn, updatedName);
        MysqlManager.closeConnection();
    }

    private static void excelManager() throws Exception {

        String sheetName = "Sheet1";
        int rowNumber = 1;

        System.out.println(ExcelManager.getRowAsMap(sheetName, rowNumber));
        System.err.println(ExcelManager.getColumnsAsMap(sheetName));
    }

    private static void jsonManager() {

        System.out.println(JsonManager.readFromJson("id"));
        JsonManager.writeToJson("name", "Anuj Gupta");
        System.out.println(JsonManager.readFromJson("name"));
        JsonManager.writeToJson("id", "1");
    }

    private static void csvManager() throws Exception {

        System.out.println(CsvManager.readFromCSV(1, 1));
        CsvManager.writeToCSV("dandy", 1, 1);
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
