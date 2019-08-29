package jsonManager;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonManager {

    private static String filePath = System.getProperty("user.dir") + "/src/test/resources/data/sample.json";
    private static ObjectMapper mapper = new ObjectMapper();

    public static void replaceJsonVariable(Map<String, Object> map, String key, String value) {

        if (map.get(key).toString().contains(key))
            map.put(key, value);
    }

    public static Map<String, Object> readFromJson() throws Exception {

        return mapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {
        });
    }

    public static void writeToJson(Map<String, Object> map) throws Exception {

        mapper.writeValue(new File(filePath), map);
    }
}
