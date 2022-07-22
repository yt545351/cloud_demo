package com.example.system.entity;

import lombok.Data;

/**
 * 老师
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class Teacher implements Cloneable {
    private int age;
    private String name;

    @Override
    public Teacher clone() throws CloneNotSupportedException {
        return (Teacher) super.clone();
    }
}
