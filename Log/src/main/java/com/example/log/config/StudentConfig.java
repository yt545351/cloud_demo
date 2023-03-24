package com.example.log.config;

import com.example.log.vo.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    @Bean
    public Student getInfo() {
        Student student = new Student();
        student.setName("张三");
        student.setAge("20");
        student.setSex("男");
        return student;
    }
}
