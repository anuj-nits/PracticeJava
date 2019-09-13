package dataProviderManager;

import excelManager.ExcelManager;
import logManager.LogManager;
import org.testng.annotations.DataProvider;
import propertyManager.PropertyManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataProviderManager {

    private String excelPath = PropertyManager.getProperty("excelPath");
    private ExcelManager excelManager = ExcelManager.getInstance(excelPath);

    @DataProvider(name = "dp")
    public Object[][] dataProvider(Method m) {

        Object[][] obj = null;
        try {
            excelManager.openInputStream();
            int rowCount = excelManager.getRowCount(m.getName()) - 1;
            int colCount = excelManager.getColumnCount(m.getName());

            obj = new Object[rowCount][1];

            for (int i = 0; i < rowCount; i++) {
                Map<String, String> map = new HashMap<>();

                for (int j = 0; j < colCount; j++) {
                    map.put(excelManager.getCellData(m.getName(), 0, j), excelManager.getCellData(m.getName(), i + 1, j));
                }
                obj[i][0] = map;
                excelManager.closeInputStream();
            }
        } catch (Exception e) {
            LogManager.error(e.getMessage());
        }
        return obj;
    }
}
