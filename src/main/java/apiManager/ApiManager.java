package apiManager;

import excelManager.ExcelManager;

import java.util.Map;

public class ApiManager {

    public static void main(String[] args) throws Exception {

        //read excel, store values in map -- done
        //read json, replace variables from excel map in json
        //call api
        //store result in map
        Map<String, String> excelData = ExcelManager.getRowAsMap("sheet1", 1);
        System.out.println(excelData);
    }
}
