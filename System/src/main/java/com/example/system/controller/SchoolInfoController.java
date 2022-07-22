package com.example.system.controller;

import com.example.system.entity.SchoolInfo;
import com.example.system.service.SchoolInfoService;
import com.example.system.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yt
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/schoolInfo")
@Api(tags = "学校信息")
public class SchoolInfoController {

    @Autowired
    private SchoolInfoService schoolInfoService;

    @ApiOperation(value = "插入学习信息")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insertSchoolInfo(@RequestBody SchoolInfo schoolInfo) {
        return new ResultBody<>(schoolInfoService.insertSchoolInfo(schoolInfo));
    }

    @ApiOperation(value = "获取列表")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getSchoolInfoList() {
        return new ResultBody<>(schoolInfoService.getSchoolInfoList());
    }
}
