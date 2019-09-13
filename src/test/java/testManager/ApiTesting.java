package testManager;

import apiManager.ApiManager;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTesting {

    private Map<String, String> data = new HashMap<>();
    private ApiManager apiManager = ApiManager.getInstance();

    @Test(priority = 1)
    public void discountDiscount() throws Exception {

        data.put("apiName", "dissociateDiscount");
        data.put("accountNumber", "ELITE010188");
        data.put("discountName", "Customer_StliQA_301051139");
        String result = apiManager.api(data);
        System.err.println(result);
    }

    @Test(priority = 2)
    public void createCity() throws Exception {

        data.put("apiName", "createCity");
        String result = apiManager.api(data);
        System.err.println(result);
    }

    @Test(priority = 3)
    public void createCustomerAccount() throws Exception {

        data.put("apiName", "createCustomerAccount");
        data.put("customerName", "Smith");
        data.put("customerCategory", "Default");
        data.put("emailId", "Smith@gmail.com");
        data.put("mobileNumber", "1234567890");
        String result = apiManager.api(data);
        System.err.println(result);
    }
}
