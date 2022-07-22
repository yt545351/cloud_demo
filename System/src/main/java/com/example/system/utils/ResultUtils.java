package com.example.system.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
public class ResultUtils {

    /**
     * 返回Map
     *
     * @param status  状态
     * @param message 消息
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static Map<String, Object> resultMap(boolean status, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        return map;
    }
}
