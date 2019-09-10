package apiManager;

import excelManager.ExcelManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jsonManager.JsonManager;
import propertyManager.PropertyManager;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiManager {

    private static ApiManager apiManager;
    private String excelPath = PropertyManager.getProperty("excelPath");
    private ExcelManager excelManager = ExcelManager.getInstance(excelPath);
    private JsonManager jsonManager = JsonManager.getInstance();

    public static ApiManager getInstance() {

        if (apiManager == null)
            apiManager = new ApiManager();

        return apiManager;
    }

    public Response callApi(String apiName) throws Exception {

        String filePath = PropertyManager.getProperty("baseDataPath") + apiName;
        Map<String, String> excelData = excelManager.getRowAsMap(apiName, 1);
        Map<String, Object> jsonMap = jsonManager.readFromJson(filePath + ".json");
        jsonMap.putAll(excelData);
        RestAssured.baseURI = PropertyManager.getProperty("api.baseURI");
        Response response = null;
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("username", PropertyManager.getProperty("api.username"));
        headerMap.put("password", PropertyManager.getProperty("api.password"));
        headerMap.put("content-type", PropertyManager.getProperty("api.content-type"));

        switch (excelData.get("method")) {

            case "post":
                response = given().
                        when().
                        headers(headerMap).
                        body(jsonMap).
                        post(jsonMap.get("payload").toString());
                break;

            case "get":
                response = given().
                        when().
                        headers(headerMap).
                        get(jsonMap.get("payload").toString());
                break;
            case "patch":
                response = given().
                        when().
                        headers(headerMap).
                        body(jsonMap).
                        patch(jsonMap.get("payload").toString());
                break;
            case "delete":
                response = given().
                        when().
                        headers(headerMap).
                        body(jsonMap).
                        delete(jsonMap.get("payload").toString());
                break;
            case "put":
                response = given().
                        when().
                        headers(headerMap).
                        body(jsonMap).
                        put(jsonMap.get("payload").toString());
                break;
            default:
                System.out.println("Invalid response found");
                break;
        }
        assert response != null;
        return response;
    }
}
