package com.example.system.controller;

import com.example.system.entity.Proclamation;
import com.example.system.service.TestService;
import com.example.system.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("测试接口")
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "定时建表任务")
    @RequestMapping(value = "/createTable", method = RequestMethod.POST)
    public Object createTable() {
        return new ResultBody<>(testService.createTable());
    }

}
