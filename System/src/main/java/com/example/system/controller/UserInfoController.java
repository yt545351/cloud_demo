package com.example.system.controller;

import com.example.system.entity.UserInfo;
import com.example.system.service.UserInfoService;
import com.example.system.vo.LoginVO;
import com.example.system.vo.QueryUserInfoVO;
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
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户验证")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "登录验证")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginVO loginVo) {
        return new ResultBody<>(userInfoService.login(loginVo));
    }

    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insertUser(@RequestBody UserInfo userInfo) {
        return new ResultBody<>(userInfoService.insertUser(userInfo));
    }

    @ApiOperation(value = "修改用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateUser(@RequestBody UserInfo userInfo) {
        return new ResultBody<>(userInfoService.updateUser(userInfo));
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteUser(@RequestBody UserInfo userInfo) {
        return new ResultBody<>(userInfoService.deleteUser(userInfo));
    }

    @ApiOperation(value = "获取用户列表")
    @RequestMapping(value = "/getUserInfoList", method = RequestMethod.POST)
    public Object getUserInfoList(@RequestBody QueryUserInfoVO queryVO) {
        return new ResultBody<>(userInfoService.getUserInfoList(queryVO));
    }

    @ApiOperation(value = "获取用户菜单权限")
    @RequestMapping(value = "/getUserMenu", method = RequestMethod.POST)
    public Object getUserMenu(@RequestBody QueryUserInfoVO queryVO) {
        return new ResultBody<>(userInfoService.getUserMenu(queryVO));
    }

}
