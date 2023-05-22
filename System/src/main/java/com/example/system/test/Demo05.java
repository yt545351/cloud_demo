package com.example.system.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo05 {
    public static void main(String[] args) {
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("ids", "510000,510100");
        map.put("names", "四川省,成都市");
        mapList.add(map);
        String ids = "510000";
        String names = "四川省";
        getUserInfo(ids, names, mapList);

    }

    public static void getUserInfo(String ids, String names, List<Map<String, String>> mapList) {
        String[] idSplit = ids.split(",");
        String[] nameSplit = names.split(",");
        String[] userIdSplit = {};
        String[] userNameSplit = {};

        for (Map<String, String> map : mapList) {
            String[] ids1 = map.get("ids").split(",");
            String[] names1 = map.get("names").split(",");
            if (userIdSplit.length == 0) {
                userIdSplit = ids1;
                userNameSplit = names1;
            }
            if (userIdSplit.length > ids1.length) {
                userIdSplit = ids1;
                userNameSplit = names1;
            }
        }
        if (idSplit.length == 1 && userIdSplit.length != 1) {
            idSplit = userIdSplit;
            nameSplit = userNameSplit;
        }

        for (int i = 0; i < idSplit.length; i++) {
            System.out.println(idSplit[i]);
            System.out.println(nameSplit[i]);
        }
    }
}
