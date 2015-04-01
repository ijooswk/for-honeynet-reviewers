package com.fan2fan.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Util functions for test
 * @author huangsz
 */
@RunWith(JUnit4.class)
public class Helper {
    /**
     * The common media type sent and received by the rest client
     */
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    /**
     * the returned json usually is {result: success}
     */
    public static final String JSON_RESULT_KEY = "$.result";

    /**
     * the returned json's obj is usually {object: {xxx:xxx, xxx:xxx}}
     */
    public static final String JSON_OBJ_KEY = "$.object";

    /**
     * the jsonMapper to write entity to sql string or read sql string to entity
     */
    public static ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * set a mock request's contentType to be json and accept json
     * @param request
     */
    public static void setRequestAllJsonType(MockHttpServletRequestBuilder request) {
        request.contentType(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));

    }

    /**
     * get the returned json's Object with key "object"
     * @param returnJson
     * @return
     */
    public static<T> T getReturnJsonObject(String returnJson, Class<T> clazz) throws Exception {
        Map jsonMap = jsonMapper.readValue(returnJson, Map.class);
        return jsonMapper.readValue(jsonMapper.writeValueAsString(jsonMap.get("object")), clazz);
    }

    /**
     * get the return json's array
     * @param returnJson
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static<T> List<T> getReturnJsonArray(String returnJson, Class<T> clazz) throws Exception {
        // return jsonMapper.readValue(returnJson, List.class) this won't work
        List array = jsonMapper.readValue(returnJson, List.class);
        List<T> result = new ArrayList<>(array.size());
        for (Object obj : array) {
            result.add(jsonMapper.readValue(jsonMapper.writeValueAsString(obj), clazz));
        }
        return result;
    }

    /**
     * Create JSON Object according to the key-value list
     * @param pairs
     * @return
     */
    public static JSONObject createJSONObject(List<Pair<String, Object>> pairs) {
        JSONObject obj = new JSONObject();
        for (Pair<String, Object> pair: pairs) {
            obj.put(pair.getKey(), pair.getValue());
        }
        return obj;
    }

    @Test
    public void testCreateJSONObject() {
        List<Pair<String, Object>> input = new ArrayList<>();
        input.add(new Pair<>("key1", 1L));
        input.add(new Pair<>("key2", "val1"));
        assertThat(createJSONObject(input).toString(), equalTo("{\"key1\":1,\"key2\":\"val1\"}"));
    }
}
