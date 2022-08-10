package com.example.system.utils;

import lombok.Data;

/**
 * 字符串工具类
 *
 * @author laughlook
 * @date 2022/08/08
 */
@Data
public class StringUtils {
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("")) {
            return false;
        }
        return true;
    }
}
