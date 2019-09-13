package testManager;

import apiManager.ApiManager;
import dataProviderManager.DataProviderManager;
import org.testng.annotations.Test;

import java.util.HashMap;

public class ApiTesting {

    private ApiManager apiManager = ApiManager.getInstance();

    @Test(priority = 1, dataProviderClass = DataProviderManager.class, dataProvider = "dp")
    public void dissociateDiscount(HashMap<String, String> map) throws Exception {

        map.put("apiName", "dissociateDiscount");
        map.put("accountNumber", "ELITE010188");
        map.put("discountName", "Customer_StliQA_301051139");
        apiManager.api(map);
    }

    @Test(priority = 2, dataProviderClass = DataProviderManager.class, dataProvider = "dp")
    public void createCity(HashMap<String, String> map) throws Exception {

        map.put("apiName", "createCity");
        apiManager.api(map);
    }

    @Test(priority = 3, dataProviderClass = DataProviderManager.class, dataProvider = "dp")
    public void createCustomerAccount(HashMap<String, String> map) throws Exception {

        map.put("apiName", "createCustomerAccount");
        map.put("customerName", "Smith");
        map.put("customerCategory", "Default");
        map.put("emailId", "Smith@gmail.com");
        map.put("mobileNumber", "1234567890");
        apiManager.api(map);
    }
}
