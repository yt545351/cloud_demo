package com.example.system.controller;

import com.example.system.entity.Student;
import com.example.system.service.StudentService;
import com.example.system.vo.QueryStudentVO;
import com.example.system.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yt
 * @since 2022-05-12
 */
@RestController
@RequestMapping("/student")
@Api(tags = "学生信息")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "查询学生信息列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Object getStudentInfo(@RequestBody QueryStudentVO queryVo) {
        return new ResultBody<>(studentService.getStudentInfo(queryVo));
    }


    @ApiOperation(value = "修改学生信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateStudentInfo(@RequestBody Student student) {
        return new ResultBody<>(studentService.updateStudentInfo(student));
    }


    @ApiOperation(value = "删除学生信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteStudentInfo(@RequestBody Student student) {
        return new ResultBody<>(studentService.deleteStudentInfo(student));
    }


    @ApiOperation(value = "查询年级列表")
    @RequestMapping(value = "/getGradeList", method = RequestMethod.POST)
    public Object getGradeList() {
        return new ResultBody<>(studentService.getGradeList());
    }


    @ApiOperation(value = "查询班级列表")
    @RequestMapping(value = "/getClassList", method = RequestMethod.POST)
    public Object getClassList(@RequestBody QueryStudentVO queryVo) {
        return new ResultBody<>(studentService.getClassList(queryVo));
    }


    @ApiOperation(value = "插入学生信息")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insertStudentInfo() {
        studentService.insertStudentInfo();
    }
}
