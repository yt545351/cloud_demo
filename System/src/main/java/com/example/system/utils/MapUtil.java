package com.example.system.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Map工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class MapUtil {
    public static void main(String[] args) {
        getOrDefault();
    }

    /**
     * 当Map集合中有这个key时，就使用这个key对应的value值，如果没有这个key就使用默认值defaultValue
     */
    public static void getOrDefault() {
        Map<String, Object> map = new HashMap<>();
        map.put("400", "123");

        Object success = map.getOrDefault("400", "success");
        log.info("{}", success);
    }
}
