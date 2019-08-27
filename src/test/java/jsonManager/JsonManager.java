package jsonManager;

import logger.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonManager {

    private static String filePath = System.getProperty("user.dir") + "/src/test/resources/data/sample.json";

    public static String readFromJson(String key) {

        JSONParser parser = new JSONParser();
        String locatorvalue = null;

        try (FileReader jfile = new FileReader(filePath)) {
            Object obj = parser.parse(jfile);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray elem = (JSONArray) jsonObject.get("prerequisites");

            Iterator<JSONObject> iterator = elem.iterator();

            while (iterator.hasNext()) {
                JSONObject elemObj = iterator.next();
                locatorvalue = (String) elemObj.get(key);
            }

        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return locatorvalue;
    }

    public static void writeToJson(String objKey, String objValue) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray dataid = (JSONArray) jsonObject.get("prerequisites");

            JSONArray newobj = new JSONArray();
            JSONObject merge = new JSONObject();
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);

            Iterator<JSONObject> iterator = dataid.iterator();

            JSONObject JObjject = new JSONObject();
            while (iterator.hasNext()) {
                JObjject = iterator.next();
                JObjject.put(objKey, objValue);
            }
            newobj.add(JObjject);

            HashMap<String, JSONArray> itrhm = new HashMap<>();
            itrhm.put("prerequisites", dataid);

            // Get a set of the entries
            Set set = itrhm.entrySet();

            // Get an iterator
            Iterator i = set.iterator();

            // Display elements
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                merge.put("prerequisites", newobj);
            }
            // Writing to a file
            fileWriter.write(merge.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }
}
