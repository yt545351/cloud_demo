package com.example.system.controller;

import com.example.system.entity.Proclamation;
import com.example.system.service.ProclamationService;
import com.example.system.vo.QueryProclamationVO;
import com.example.system.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yt
 * @since 2022-05-20
 */
@RestController
@RequestMapping("/proclamation")
@Api(tags = "公告管理")
public class ProclamationController {
    @Autowired
    private ProclamationService proclamationService;

    @ApiOperation(value = "新增公告")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insertProclamation(@RequestBody Proclamation proclamation) {
        return new ResultBody<>(proclamationService.insertProclamation(proclamation));
    }

    @ApiOperation(value = "修改公告")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateProclamation(@RequestBody Proclamation proclamation) {
        return new ResultBody<>(proclamationService.updateProclamation(proclamation));
    }

    @ApiOperation(value = "修改公告")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteProclamation(@RequestBody Proclamation proclamation) {
        return new ResultBody<>(proclamationService.deleteProclamation(proclamation));
    }

    @ApiOperation(value = "获取最新一条公告")
    @RequestMapping(value = "/getOne", method = RequestMethod.POST)
    public Object getOne() {
        return new ResultBody<>(proclamationService.getOne());
    }

    @ApiOperation(value = "查询公告列表")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getProclamationList(@RequestBody QueryProclamationVO queryVO) {
        return new ResultBody<>(proclamationService.getProclamationList(queryVO));
    }

    @ApiOperation(value = "导出列表")
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public void exportExcel(@RequestBody QueryProclamationVO queryVO, HttpServletResponse response) {
        proclamationService.exportExcel(queryVO, response);
    }
}
