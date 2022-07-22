package com.example.system.entity;

import lombok.Data;

/**
 * 学生一
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class Student1 implements Cloneable {
    public String name;
    public int age;

    @Override
    public Student1 clone() throws CloneNotSupportedException {
        return (Student1) super.clone();
    }
}
