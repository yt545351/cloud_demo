package com.example.system.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Demo03 {
    public static void main(String[] args) {
        List<String> headList = new ArrayList<>();
        headList.add("姓名");
        headList.add("年龄");
        headList.add("性别");
        List<List<String>> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> list = new ArrayList<>();
            list.add("张三" + i);
            list.add("18" + i);
            list.add("男");
            dataList.add(list);
        }
        dataList.add(0,headList);
        System.out.println(dataList);
        String rate = getRate(1, 1);
        System.out.println(rate);
    }


    private static String getRate(Integer a, Integer b) {
        BigDecimal rate = new BigDecimal(0);
        if (a != 0) {
            rate = new BigDecimal(a).multiply(new BigDecimal(100)).divide(new BigDecimal(b), 2, BigDecimal.ROUND_HALF_UP);
        }
        return rate.toString();
    }
}
