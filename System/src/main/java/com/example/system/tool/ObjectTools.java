package com.example.system.tool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象工具
 *
 * @author laughlook
 * @date 2022/08/12
 */
public class ObjectTools {
    /**
     * 获取对象属性列表
     *
     * @param obj obj
     * @return {@link List}<{@link Object}>
     */
    public static List<Object> getObjectPropertyList(Object obj) {
        List<Object> propertyList = new ArrayList<>();
        //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            //设置允许通过反射访问私有变量
            field.setAccessible(true);
            //获取字段属性名称
            String property = field.getName();
            //排除serialVersionUID
            if (!property.equals("serialVersionUID")) {
                propertyList.add(property);
            }
        }
        return propertyList;
    }

    /**
     * 获取对象值列表
     *
     * @param obj obj
     * @return {@link List}<{@link Object}>
     */
    public static List<Object> getObjectValueList(Object obj) {
        List<Object> valueList = new ArrayList<>();
        //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            //设置允许通过反射访问私有变量
            field.setAccessible(true);
            //获取字段属性名称
            String name = field.getName();
            //排除serialVersionUID
            if (!name.equals("serialVersionUID")) {
                Object value = null;
                try {
                    //获取属性值
                    value = field.get(obj);
                    valueList.add(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return valueList;
    }

}
