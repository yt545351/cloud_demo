package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.Student;
import com.example.system.vo.QueryStudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2022-05-12
 */
@Mapper
@Repository
public interface StudentMapper extends BaseMapper<Student> {

    List<Map<String, Object>> queryClassTeacherName(@Param("queryVo") QueryStudentVO queryStudentVO);
}
