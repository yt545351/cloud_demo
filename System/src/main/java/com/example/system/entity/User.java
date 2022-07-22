package com.example.system.entity;

import lombok.Data;

/**
 * 用户
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class User {
    private String name;
    private Integer age;
    private String sex;

    public User() {
    }

    public User(String name, Integer age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

}
