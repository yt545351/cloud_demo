package com.example.system.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * BigDecimal工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class BigDecimalUtil {

    static Random random = new Random();

    public static void main(String[] args) {
        divide();
        randomNum();
    }

    /**
     * BigDecimal除法
     */
    public static void divide() {
        BigDecimal a = new BigDecimal(50);
        BigDecimal b = new BigDecimal(3);
        //四舍五入,保留2位小数
        BigDecimal divide = a.divide(b, 2, BigDecimal.ROUND_HALF_UP);

        log.info("{}", divide);
    }

    /**
     * 随机保留4位小数
     */
    public static void randomNum() {
        BigDecimal divide = new BigDecimal(random.nextInt(9700 - 9400) + 9400)
                .divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP);
        log.info("{}", divide);
    }
}
