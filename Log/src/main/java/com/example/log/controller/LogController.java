package com.example.log.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.example.log.config.StudentConfig;
import com.example.log.vo.Student;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RequestMapping
@RestController
@Slf4j
@RefreshScope
public class LogController {

    @RequestMapping(value = "/query/{ss}", method = RequestMethod.GET)
    private String query(@PathVariable("ss") String ss) {
        log.info("{}", 111);
        return ss;
    }

    //    @NacosValue(value = "${my.name}",autoRefreshed = true)
    @Value("${my.name}")
    private String name;
    //    @NacosValue(value = "${my.age}",autoRefreshed = true)
    @Value("${my.age}")
    private String age;

    @ApiOperation(value = "测试接口")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        System.out.println(name + age);
    }

    @ApiOperation(value = "测试接口")
    @GetMapping(value = "/exportTest")
    public void exportTest(HttpServletResponse response) throws IOException {
        List<Object> headList = new ArrayList<>();
        headList.add("姓名");
        headList.add("年龄");
        headList.add("性别");
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Object> list = new ArrayList<>();
            list.add("张三" + i);
            list.add("18" + i);
            list.add("男");
            dataList.add(list);
        }
        dataList.add(0, headList);
        exportFile(dataList, "111", response);
    }

    public void exportFile(List<List<Object>> dataList, String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(dataList, false);
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }
}
