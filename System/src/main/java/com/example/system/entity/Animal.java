package com.example.system.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 动物
 *
 * @author laughlook
 * @date 2022/08/11
 */
@Data
public class Animal {
    @ExcelProperty("名字name")
    private String name;
    @ExcelProperty("性别sex")
    private String sex;
    @ExcelProperty("年龄age")
    private Integer age;
}
