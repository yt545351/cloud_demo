package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.Student;
import com.example.system.vo.QueryStudentVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yt
 * @since 2022-05-12
 */
public interface StudentService extends IService<Student> {

    Object getStudentInfo(QueryStudentVO queryVo);

    void insertStudentInfo();

    Object getGradeList();

    Object getClassList(QueryStudentVO queryVo);

    Object updateStudentInfo(Student student);

    Object deleteStudentInfo(Student student);
}
