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
        telEncrypt();
    }

    /**
     * 号码截取加密
     */
    public static void telEncrypt() {
        String ss = "15708447219";
        String s = ss.substring(0, 3) + "****" + ss.substring(7, 11);
        log.info("{}", s);
    }
}
