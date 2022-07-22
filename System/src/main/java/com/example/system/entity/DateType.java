package com.example.system.entity;

/**
 * 日期类型
 *
 * @author laughlook
 * @date 2022/07/21
 */
public enum DateType {
    min(1), hour(2), day(3), week(4), mouth(5), quarter(6), year(7);

    private Integer val;

    DateType(Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }
}
