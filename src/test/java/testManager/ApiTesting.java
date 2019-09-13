package testManager;

import apiManager.ApiManager;
import excelManager.ExcelManager;
import io.restassured.path.json.JsonPath;
import logManager.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import propertyManager.PropertyManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApiTesting {

    private ApiManager apiManager = ApiManager.getInstance();
    private String excelPath = PropertyManager.getProperty("excelPath");
    private ExcelManager excelManager = ExcelManager.getInstance(excelPath);

    @BeforeSuite
    public void beforeSuite() {
        LogManager.setup();
    }

    @Test(priority = 1, dataProvider = "dp")
    public void dissociateDiscount(HashMap<String, String> map) throws Exception {

        map.put("apiName", "dissociateDiscount");
        map.put("accountNumber", "ELITE010188");
        map.put("discountName", "Customer_StliQA_301051139");
        JsonPath result = apiManager.api(map);
        int responseCode = result.get("responseCode");
        String responseMessage = result.get("responseMessage");
        System.out.println(responseCode);
        System.out.println(responseMessage);
    }

    @Test(priority = 2, dataProvider = "dp")
    public void createCity(HashMap<String, String> map) throws Exception {

        map.put("apiName", "createCity");
        JsonPath jp = apiManager.api(map);
        Assert.assertEquals(map.get("responseCode"), jp.get("responseCode").toString(), "Invalid response code found");
        Assert.assertEquals(map.get("responseMessage"), jp.get("responseMessage"), "Invalid response message found");
    }

    @Test(priority = 3, dataProvider = "dp")
    public void createCustomerAccount(HashMap<String, String> map) throws Exception {

        map.put("apiName", "createCustomerAccount");
        map.put("customerName", "Smith");
        map.put("customerCategory", "Default");
        map.put("emailId", "Smith@gmail.com");
        map.put("mobileNumber", "1234567890");
        JsonPath result = apiManager.api(map);
        int responseCode = result.get("responseCode");
        String responseMessage = result.get("responseMessage");
        System.out.println(responseCode);
        System.out.println(responseMessage);
    }

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
