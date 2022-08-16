package com.example.system.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class StringUtil {
    public static void main(String[] args) {

    }

    /**
     * 电话加密
     *
     * @param phoneNumber 电话号码
     * @return {@link String}
     */
    public static String telEncrypt(String phoneNumber) {
        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11);
    }
}
