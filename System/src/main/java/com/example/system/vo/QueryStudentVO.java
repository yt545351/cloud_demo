package com.example.system.vo;

import lombok.Data;

/**
 * 查询学生入参
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class QueryStudentVO extends Page {
    private String name;
    private Integer grade;
    private Integer class_name;
}
