package com.example.system.utilsOth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 下划线转小驼峰
 *
 * @author laughlook
 * @date 2022/07/21
 */
public class UnderlineToSmallHump {
    static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");
    static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

    public static void main(String[] args) {
        String s = "http_res_cnt_4g";
        String s1 = lineToHump(s);
        System.out.println(s1);
    }

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
