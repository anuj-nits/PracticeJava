package apiManager;

import excelManager.ExcelManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import logManager.LogManager;
import org.json.JSONObject;
import propertyManager.PropertyManager;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ApiManager {

    private static ApiManager apiManager;
    private String excelPath = PropertyManager.getProperty("excelPath");
    private ExcelManager excelManager = ExcelManager.getInstance(excelPath);
    private Map<String, String> data = new HashMap<>();

    public static ApiManager getInstance() {

        if (apiManager == null)
            apiManager = new ApiManager();

        return apiManager;
    }

    private Map<String, String> createPayload(String payload) {

        for (Map.Entry<String, String> m : data.entrySet())
            payload = payload.replace("{" + m.getKey() + "}", m.getValue());
        data.put("payload", payload);
        return data;
    }

    private String createBody() throws Exception {

        String fileName = PropertyManager.getProperty("baseDataPath") + data.get("apiName") + ".json";
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        JSONObject jsonObject = new JSONObject(content);
        String jsonFileToString = jsonObject.toString();

        for (Map.Entry<String, String> m : data.entrySet()) {
            jsonFileToString = jsonFileToString.replace("${" + m.getKey() + "}", m.getValue());
        }

        return jsonFileToString;
    }

    public JsonPath api(Map<String, String> d) throws Exception {

        LogManager.setup();
        data = excelManager.getRowAsMap(d.get("apiName"), 1);
        data.putAll(d);
        data = createPayload(data.get("payload"));
        String jsonFileToString = createBody();

        RestAssured.baseURI = PropertyManager.getProperty("api.baseURI");
        Response response = null;
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("username", PropertyManager.getProperty("api.username"));
        headerMap.put("password", PropertyManager.getProperty("api.password"));
        headerMap.put("content-type", PropertyManager.getProperty("api.content-type"));
        RequestSpecification request = RestAssured.given().headers(headerMap);

        switch (data.get("method")) {

            case "post":
                response = request.
                        body(jsonFileToString).
                        post(data.get("payload"));
                break;

            case "get":
                response = request.
                        get(data.get("payload"));
                break;
            case "patch":
                response = request.
                        body(jsonFileToString).
                        patch(data.get("payload"));
                break;
            case "delete":
                response = request.
                        body(jsonFileToString).
                        delete(data.get("payload"));
                break;
            case "put":
                response = request.
                        body(jsonFileToString).
                        put(data.get("payload"));
                break;
            default:
                System.out.println("Invalid response found");
                break;
        }
        assert response != null;
        response.then().assertThat().statusCode(Integer.parseInt(data.get("statusCode")));
        return response.jsonPath();
    }
}
