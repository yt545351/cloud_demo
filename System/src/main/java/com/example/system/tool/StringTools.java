package com.example.system.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author laughlook
 * @date 2022/08/12
 */
public class StringTools {
    static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");
    static final Pattern LINE_PATTERN = Pattern.compile("_[a-z]");

    /**
     * 下划线转小驼峰
     *
     * @param str str
     * @return {@link String}
     */
    public static String underlineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(0).toUpperCase().replace("_", ""));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 小驼峰转下划线
     *
     * @param str str
     * @return {@link String}
     */
    public static String humpToUnderline(String str) {
        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str str
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }


}
