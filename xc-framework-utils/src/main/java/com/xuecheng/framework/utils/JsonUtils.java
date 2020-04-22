package com.xuecheng.framework.utils;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

/**
 * json utils
 * <p>
 * Created by wzy on 2016/10/7.
 */
public class JsonUtils {

    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        return objectMapper;
    }

    public static String toJson(Object object) {
        if (!CheckUtils.isNull(object)) {
            try {
                return getObjectMapper().writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        if (CheckUtils.isNotEmpty(json)) {
            try {
                return getObjectMapper().readValue(json, tClass);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public static <T> T fromJson(String json, TypeReference<T> type) {
        if (CheckUtils.isNotEmpty(json)) {
            try {
                return getObjectMapper().readValue(json, type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        @SuppressWarnings("unchecked")
        List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
        return ts;
    }

    /**
     * 使用gson校验字符串是否是json格式
     *
     * @param jsonStr
     * @return
     */
    public static boolean validate(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

}
