package com.example.system.utils;

import com.example.system.entity.Student1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * List工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class ListUtil {
    public static void main(String[] args) {
        getList();
        listInsert();
//        threadList();
//        getObjectFieldAndValue();
    }


    /**
     * 获取List<对象>指定属性集合
     */
    public static void getList() {
        List<Student1> list = new ArrayList<Student1>();
        for (int i = 0; i < 10; i++) {
            Student1 stu = new Student1();
            stu.setAge(i);
            stu.setName("name" + i);
            list.add(stu);
        }
        log.info("{}", list);
        List<String> collect = list.stream().map(Student1::getName).collect(Collectors.toList());
        log.info("{}", collect);
    }

    /**
     * list指定位置插入
     */
    public static void listInsert() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(0, i);
        }
        log.info("{}", list);
    }

    /**
     * 多线程处理List,注意线程安全
     */
    @SneakyThrows
    public static void threadList() {
        Map<String, String> dataMap = new HashMap<>();
        for (int i = 1; i <= 21; i++) {
            dataMap.put(i + "", i + "_" + i);
        }
        List<String> result = Collections.synchronizedList(new ArrayList<String>());
        // 开始时间
        long start = System.currentTimeMillis();

        List<String> list = Collections.synchronizedList(new ArrayList<String>());
        for (int i = 1; i <= 21; i++) {
            list.add(i + "");
        }

        // 每6条数据开启一条线程
        int threadSize = 6;
        // 总数据条数
        int dataSize = list.size();
        // 线程数
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;

        // 创建一个线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // 定义一个任务集合
        List<Callable<Integer>> tasks = new ArrayList<>();
        Callable<Integer> task = null;
        List<String> cutList = null;

        // 确定每条线程的数据
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                if (special) {
                    break;
                }
                cutList = list.subList(threadSize * i, dataSize);
            } else {
                cutList = list.subList(threadSize * i, threadSize * (i + 1));
            }
            final List<String> listStr = cutList;
            task = new Callable<Integer>() {

                @Override
                public Integer call() {
                    log.info("{}线程:{}", Thread.currentThread().getName(), listStr);
                    List<String> list = Collections.synchronizedList(new ArrayList<String>());
                    for (String s : listStr) {
                        String s1 = dataMap.get(s);
                        list.add(s1);
                    }
                    result.addAll(list);
                    return 1;
                }
            };
            // 这里提交的任务容器列表和返回的Future列表存在顺序对应的关系
            tasks.add(task);
        }

        List<Future<Integer>> results = exec.invokeAll(tasks);

        for (Future<Integer> future : results) {
            log.info("{}", future.get());
        }

        // 关闭线程池
        exec.shutdown();
        log.info("线程任务执行结束");
        log.info("执行任务消耗了:{}毫秒", System.currentTimeMillis() - start);
        log.info("{}", result);
    }

    /**
     * 获取List<Object>的属性名和属性值
     */
    public static void getObjectFieldAndValue() {
        List<Student1> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student1 student = new Student1();
            student.setAge(i);
            student.setName("zs" + i);
            list.add(student);
        }
        Map<String, List<Object>> map = new HashMap<>();
        for (Student1 student : list) {
            String[] fieldNames = getFiledName(student);

            for (String fieldName : fieldNames) {
                if (!map.containsKey(fieldName)) {
                    List<Object> fieldNameList = new ArrayList<>();
                    map.put(fieldName, fieldNameList);
                }
            }
            //获取属性的名字
            for (String name : fieldNames) {
                //获取属性的值
                Object value = getFieldValueByName(name, student);
                List<Object> objects = map.get(name);
                objects.add(value);
                map.put(name, objects);
            }
        }
        log.info("{}", map);
    }

    /**
     * 获取属性名数组
     *
     * @param o o
     * @return {@link String[]}
     */
    private static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性名数组
     *
     * @param fieldName 字段名
     * @param o         o
     * @return {@link Object}
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
