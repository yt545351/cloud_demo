package com.example.system.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

//@Component
public class MyPostConstruct {
//    @PostConstruct
    public void postMethod() {
        System.out.println(new Date() + " : 正在执行@PostConstruct注解的方法。。。。。。。。。。。。。。。");
    }
//    public MyPostConstruct() {
//        System.out.println(new Date() + " : 构造函数正在执行。。。。。。。。。。。。。。。");
//    }
}
