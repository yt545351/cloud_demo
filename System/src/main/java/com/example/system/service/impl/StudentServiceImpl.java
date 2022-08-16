package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.PageBean;
import com.example.system.entity.Student;
import com.example.system.mapper.StudentMapper;
import com.example.system.service.StudentService;
import com.example.system.tool.ResultTools;
import com.example.system.tool.StringTools;
import com.example.system.vo.QueryStudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-05-12
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Object getStudentInfo(QueryStudentVO queryVo) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>()
                .like(!StringTools.isEmpty(queryVo.getName()), "name", queryVo.getName())
                .eq(queryVo.getGrade() != null, "grade", queryVo.getGrade())
                .eq(queryVo.getClass_name() != null, "class_name", queryVo.getClass_name());
        List<Student> list = studentMapper.selectList(queryWrapper);
        PageBean<Student> pageBean = new PageBean<>(list, queryVo.getPageNum(), queryVo.getPageSize());
        return pageBean;
    }

    @Override
    public void insertStudentInfo() {
        List<Student> list = new ArrayList<Student>();
        for (int i = 1; i <= 22; i++) {
            Student student = new Student();
            student.setName("王" + i);
            student.setAge(new Random().nextInt(5) + 14);
            student.setSex(new Random().nextInt(3));
            student.setAddress("天桥" + i);
            student.setTell("1" + i);
            student.setGrade(2022);
            student.setClass_name(2);
            student.setClass_teacher_name("李四");
            student.setClass_teacher_tell("1234");
            student.setFa_name("王" + i + "fa");
            student.setFa_tell("2" + i);
            student.setMo_name("王" + i + "mo");
            student.setMo_tell("3" + i);
            list.add(student);
        }
        for (Student student : list) {
            studentMapper.insert(student);
        }
    }

    @Override
    public Object getGradeList() {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>()
                .select("distinct grade");
        List<Student> list = studentMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Object getClassList(QueryStudentVO queryVo) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>()
                .select("distinct class_name")
                .eq("grade", queryVo.getGrade());
        List<Student> list = studentMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Object updateStudentInfo(Student student) {
        int i = studentMapper.updateById(student);
        return i > 0 ? ResultTools.resultMap(true, "更新成功") : ResultTools.resultMap(false, "更新失败");
    }

    @Override
    public Object deleteStudentInfo(Student student) {
        int i = studentMapper.deleteById(student.getId());
        return i > 0 ? ResultTools.resultMap(true, "删除成功") : ResultTools.resultMap(false, "删除失败");
    }
}
