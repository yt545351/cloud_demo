package com.example.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.asm.Type;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Json工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class JsonUtil {
    public static void main(String[] args) {
        String jsonArr = "[{\"name\":\"张三\",\"age\":18,\"sex\":\"男\"},{\"name\":\"李四\",\"age\":25,\"sex\":\"女\"},{\"name\":\"王五\",\"age\":20,\"sex\":\"男\"}]";
        List<Map<String, Object>> list = jsonToList(jsonArr);
        String jsonStr1 = listToJson(list);
        System.out.println(list);
        System.out.println(jsonStr1);

        String json = "{\"name\":\"张三\",\"age\":18,\"sex\":\"男\"}";
        Map<String, Object> map = jsonToMap(json);
        System.out.println(map);
        String jsonStr2 = mapToJson(map);
        System.out.println(jsonStr2);

    }

    /**
     * json转换List<Map<String, Object>>
     *
     * @param jsonStr json str
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    public static List<Map<String, Object>> jsonToList(String jsonStr) {
        JSONArray objects = JSON.parseArray(jsonStr);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object object : objects) {
            result.add((Map<String, Object>) object);
        }
        return result;
    }

    /**
     * List<Map<String, Object>>转换json
     *
     * @param list 列表
     * @return {@link String}
     */
    public static String listToJson(List<Map<String, Object>> list) {
        return JSON.toJSONString(list);
    }

    /**
     * json转换Map<String, Object>
     *
     * @param jsonStr json str
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static Map<String, Object> jsonToMap(String jsonStr) {
        return JSON.parseObject(jsonStr);
    }

    /**
     * Map<String, Object>转换json
     *
     * @param map map
     * @return {@link String}
     */
    public static String mapToJson(Map<String, Object> map) {
        return JSON.toJSONString(map);
    }
}
