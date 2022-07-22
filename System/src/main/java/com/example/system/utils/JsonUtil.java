package com.example.system.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
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
        jsonToList();
        listToJson();
    }

    /**
     * json数组转换List，并排序
     */
    public static void jsonToList() {
        String json = "[{\"tel_name\":\"华为\",\"tel_4g_cnt\":500000,\"tel_5g_cnt\":100000},{\"tel_name\":\"Apple\",\"tel_4g_cnt\":400000,\"tel_5g_cnt\":80000},{\"tel_name\":\"OPPO\",\"tel_4g_cnt\":300000,\"tel_5g_cnt\":60000},{\"tel_name\":\"Vivo\",\"tel_4g_cnt\":200000,\"tel_5g_cnt\":40000},{\"tel_name\":\"三星\",\"tel_4g_cnt\":100000,\"tel_5g_cnt\":20000},{\"tel_name\":\"荣耀\",\"tel_4g_cnt\":80000,\"tel_5g_cnt\":10000},{\"tel_name\":\"小米\",\"tel_4g_cnt\":60000,\"tel_5g_cnt\":5000},{\"tel_name\":\"魅族\",\"tel_4g_cnt\":30000,\"tel_5g_cnt\":1000}]";
        List<Map> maps = JSON.parseArray(json, Map.class);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map map : maps) {
            Set set = map.keySet();
            Map<String, Object> hashMap = new HashMap<>();
            for (Object key : set) {
                Object value = map.get(key);
                hashMap.put(key.toString(), value);
            }
            result.add(hashMap);
        }
        for (Map<String, Object> map : maps) {
            Integer tel_4g_cnt = Integer.parseInt(map.get("tel_4g_cnt").toString());
            Integer tel_5g_cnt = Integer.parseInt(map.get("tel_5g_cnt").toString());
            BigDecimal rate = new BigDecimal(tel_4g_cnt).divide(new BigDecimal(tel_4g_cnt + tel_5g_cnt), 4, BigDecimal.ROUND_HALF_UP);
            map.put("rate", rate);
        }

        result.sort(new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                //升序
                return (int) o1.get("tel_4g_cnt") - (int) o2.get("tel_4g_cnt");
                //降序
//                return (int) o2.get("tel_4g_cnt") - (int) o1.get("tel_4g_cnt");
            }
        });
        log.info("{}", maps);

    }

    /**
     * list转换json
     */
    public static void listToJson() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> hw = new HashMap<String, Object>();
        hw.put("tel_name", "华为");
        hw.put("tel_4g_cnt", 50000);
        hw.put("tel_5g_cnt", 10000);
        Map<String, Object> pg = new HashMap<String, Object>();
        pg.put("tel_name", "苹果");
        pg.put("tel_4g_cnt", 30000);
        pg.put("tel_5g_cnt", 5000);
        list.add(hw);
        list.add(pg);
        String s = JSON.toJSONString(list);
        log.info("{}", s);
    }
}
