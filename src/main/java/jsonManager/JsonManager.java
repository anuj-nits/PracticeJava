package jsonManager;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class JsonManager {

    private static JsonManager jsonManager;
    private ObjectMapper mapper = new ObjectMapper();

    public static JsonManager getInstance() {

        if (jsonManager == null)
            jsonManager = new JsonManager();

        return jsonManager;
    }

    public void replaceJsonVariable(Map<String, Object> map, String key, String value) {

        if (map.get(key).toString().contains(key))
            map.put(key, value);
    }

    public Map<String, Object> readFromJson(String filePath) throws Exception {

        return mapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {
        });
    }

    public void writeToJson(String filePath, Map<String, Object> map) throws Exception {

        mapper.writeValue(new File(filePath), map);
    }
}
