import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Vitaly on 27.01.15.
 */
public class JSON {

    public static String method;
    public static String value;
    public static String encoded;
    public static String parseMethod(String json){

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = factory.createParser(json);
            JsonNode jnode = mapper.readTree(parser);
            method = jnode.get("method").toString();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return method.replace("\"", "");
    }
    public static String parseValue(String json){

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = factory.createParser(json);
            JsonNode jnode = mapper.readTree(parser);
            value = jnode.get("value").toString();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value.replace("\"", "");
    }
    public static String encode(String methodName, String Value){
        HashMap<String, String> map = new HashMap<String, String>();;
        map.put("method", methodName);
        map.put("value", Value);
        ObjectMapper mapper = new ObjectMapper();
        try {
            encoded = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return encoded;
    }
}
