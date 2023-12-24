package com.social.demo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈翔
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public static String object2StringSlice(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }

    }
    public static  <T> List<T> ListJson(String json, Class<T> elementType) {
        List<T> items = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            items = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static <T> T deserialize(String json, Class<T> valueType) throws IOException {
        return OBJECT_MAPPER.readValue(json, valueType);
    }

}
