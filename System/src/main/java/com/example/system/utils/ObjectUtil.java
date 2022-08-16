package com.example.system.utils;

import com.example.system.entity.Proclamation;
import com.example.system.entity.Student1;
import com.example.system.entity.Teacher;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Object工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class ObjectUtil {
    public static void main(String[] args) {
        fieldIsNull();
        clone1();
    }

    /**
     * 判断对象的每个属性不为NULL
     */
    public static void fieldIsNull() {
        Student1 s = new Student1();
        s.setName("张三");
        s.setAge(18);
        if (!objCheckIsNull(s)) {
            log.info("true");
        }
    }

    /**
     * clone()方法,对象需重写clone方法
     */
    public static void clone1() {
        Teacher teacher = new Teacher();
        teacher.setAge(18);
        teacher.setName("李四");
        try {
            Teacher clone = teacher.clone();
            log.info("{}", clone);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static boolean objCheckIsNull(Object object) {
        Class aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        boolean flag = true;
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
                log.info("{}", fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // 只要有一个属性值不为null 就返回false 表示对象不为null
            if (fieldValue != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
