package com.example.system.utils;

import com.example.system.entity.User;
import com.google.common.base.Supplier;

import java.util.function.DoubleToIntFunction;

/**
 * lumda跑龙套
 *
 * @author laughlook
 * @date 2022/09/22
 */
public class LambdaUtil {
    public static void main(String[] args) {
        Test test = (name, age) -> {
            System.out.println(name + age);
            return age + 1;
        };
        int age = test.test("张三", 18);
        System.out.println(age);
    }

}

interface Test {
    /**
     * 测试
     *
     * @param name 名字
     * @param age  年龄
     * @return int
     */
    int test(String name, int age);
}
