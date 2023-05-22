package com.example.system.service.impl;

import com.example.system.mapper.TestMapper;
import com.example.system.service.TestService;
import com.example.system.utils.annotation.MyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 测试服务impl
 *
 * @author laughlook
 * @date 2022/07/25
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    static final String DTF1 = "yyyy-MM-dd HH:mm:ss";
    static final String DTF2 = "yyyyMMdd";

    @MyAnnotation
    private static String code;

    @Autowired
    private TestMapper testMapper;

    @Override
    @Scheduled(cron = "${time.create}")
    public Object createTable() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DTF2);
        String dateStr = dtf.format(LocalDateTime.now());
        String tableName = "school_info" + "_" + dateStr;
        int i = testMapper.createTable(tableName);
        return true;
    }

    public static void main(String[] args) {
        System.out.println(code);
    }
}
