package com.example.log.controller;

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

}
