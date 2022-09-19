package com.example.system.utils;

import com.example.system.entity.QueryTime;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期转换工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class DateUtil {
    static final String DTF = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) {
//        nowToString();
//        stringToString1();
//        stringToString2();
//        localDateTimeToString();
//        getYearMonthWeekDay();
//        getFiveMinBefore();
//        localDateTimeToWholeTime();
//        getNowBefore12FiveMin();
        getEveryMonthFirstDayAndLastDay();
//        stringToLocalDateTime();


    }

    /**
     * 当前时间转换String
     */
    public static void nowToString() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String format1 = dtf1.format(now);
        String format2 = dtf2.format(now);
        String format3 = dtf3.format(now);
        System.out.println(format1);
        System.out.println(format2);
        System.out.println(format3);
    }

    /**
     * String日期(yyyyMMdd)转换String日期(yyyy-MM-dd)
     */
    public static void stringToString1() {
        String startTime = "20220509";
        String time = startTime.substring(0, 4) + "-" + startTime.substring(4, 6) + "-" + startTime.substring(6, 8);
        System.out.println(time);
    }

    /**
     * String日期(yyyy-MM-dd hh:mm:ss)转换String日期(yyyyMMdd)
     */
    public static void stringToString2() {
        String startTime = "2022-04-01 23:59:59";
        String[] arr = startTime.split(" ")[0].split("-");
        String time = arr[0] + arr[1] + arr[2];
        System.out.println(time);
    }

    /**
     * 获取年月周日
     */
    public static void getYearMonthWeekDay() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DTF);
        LocalDateTime startTime = LocalDateTime.parse("2022-01-10 00:00:00", dtf);
        log.info("当前年：{}", startTime.getYear());
        log.info("当前月：{}", startTime.getMonthValue());
        log.info("当前日：{}", startTime.getDayOfMonth());
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        int weeks = startTime.get(weekFields.weekOfYear());
        log.info("获取当前时间第：{}周", weeks - 1);
        log.info("{}", startTime);
        String[] split = startTime.toString().split("-");
        for (String s : split) {
            log.info("{}", s);
        }
    }

    /**
     * 获取指定时间5分钟前时间
     */
    public static void getFiveMinBefore() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DTF);
        LocalDateTime startTime = LocalDateTime.parse("2022-02-21 15:30:00", df);
        LocalDateTime endTime = LocalDateTime.parse("2022-02-21 15:34:59", df);

        List<QueryTime> timeList = new ArrayList<>();
        int count = 6;
        //半小时：i=6，1小时：i=12
        for (int i = 0; i < count; i++) {
            QueryTime queryTime = new QueryTime();
            startTime = startTime.minus(5, ChronoUnit.MINUTES);
            endTime = endTime.minus(5, ChronoUnit.MINUTES);
            queryTime.setBeginTime(startTime);
            queryTime.setEndTime(endTime);
            timeList.add(queryTime);
        }
        for (QueryTime queryTime : timeList) {
            log.info("{}", df.format(queryTime.getBeginTime()));
            log.info("{}", df.format(queryTime.getEndTime()));
            log.info("--------------");
        }
    }

    /**
     * 时间转换整点
     */
    public static void localDateTimeToWholeTime() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DTF);
        String format = df.format(LocalDateTime.now());
        String[] split = format.split(":");
        String endTime = split[0] + ":00:00";

        LocalDateTime parse = LocalDateTime.parse(endTime, df);
        log.info("{}", parse);
    }

    /**
     * 获取当前时间12个5分钟之前的时间
     */
    public static void getNowBefore12FiveMin() {
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> minList = new ArrayList<>();
        LocalDateTime min = now;
        int count1 = 12;
        for (int i = 0; i < count1; i++) {
            minList.add(min);
            min = min.minusMinutes(5);
        }
        for (LocalDateTime localDateTime : minList) {
            log.info("{}", localDateTime);
        }
        log.info("-----------------");
        List<LocalDateTime> hourList = new ArrayList<>();
        LocalDateTime hour = now;
        int count2 = 12;
        for (int i = 0; i < count2; i++) {
            hourList.add(hour);
            hour = hour.minusHours(1);
        }
        for (LocalDateTime localDateTime : hourList) {
            log.info("{}", localDateTime);
        }
    }

    /**
     * 获取每个月第一天和最后一天时间
     */
    public static void getEveryMonthFirstDayAndLastDay() {
        LocalDateTime now = LocalDateTime.now();
        now = now.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0);
        int count = 7;
        for (int i = 1; i <= count; i++) {
            LocalDateTime startTime = now.minusMonths(i);
            LocalDateTime endTime = startTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
            log.info("-----------------");
            log.info("{}", startTime);
            log.info("{}", endTime);
        }
    }

    /**
     * String转换localDateTime
     */
    public static void stringToLocalDateTime() {
        String ss = "2022-05-20 00:00:00";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DTF);
        LocalDateTime parse = LocalDateTime.parse(ss, dtf);
        log.info("{}", parse);
    }
}
