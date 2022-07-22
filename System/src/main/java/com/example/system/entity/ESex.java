package com.example.system.entity;

/**
 * 性别
 *
 * @author laughlook
 * @date 2022/07/21
 */
public enum ESex {

    woman(0), man(1), oth(3);

    private Integer sex;

    ESex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSex() {
        return sex;
    }
}
