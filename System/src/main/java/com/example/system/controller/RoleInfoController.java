package com.example.system.controller;

import com.example.system.entity.RoleInfo;
import com.example.system.service.RoleInfoService;
import com.example.system.vo.QueryRoleInfoVO;
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
 * @since 2022-05-27
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色权限")
public class RoleInfoController {
    @Autowired
    private RoleInfoService roleInfoService;

    @ApiOperation(value = "新增角色")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insertRole(@RequestBody RoleInfo roleInfo) {
        return new ResultBody<>(roleInfoService.insertRole(roleInfo));
    }

    @ApiOperation(value = "删除角色")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteRole(@RequestBody RoleInfo roleInfo) {
        return new ResultBody<>(roleInfoService.deleteRole(roleInfo));
    }

    @ApiOperation(value = "更新角色")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateRole(@RequestBody RoleInfo roleInfo) {
        return new ResultBody<>(roleInfoService.updateRole(roleInfo));
    }

    @ApiOperation(value = "获取角色列表")
    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    public Object getRoleList(@RequestBody QueryRoleInfoVO queryVO) {
        return new ResultBody<>(roleInfoService.getRoleList(queryVO));
    }

}
