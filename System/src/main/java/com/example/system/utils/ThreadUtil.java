package com.example.system.utils;

import com.example.system.entity.MyRunnable;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
public class ThreadUtil {
    public static void main(String[] args) {
//        thread1();
        thread2();
    }

    /**
     * 线程，需手动创建
     */
    public static void thread1() {
        MyRunnable mr = new MyRunnable();
        Thread t1 = new Thread(mr, "窗口1");
        Thread t2 = new Thread(mr, "窗口2");
        Thread t3 = new Thread(mr, "窗口3");
        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * 定长线程池(newFixedThreadPool)
     */
    @SneakyThrows
    public static void thread2() {

//        for (int i = 100; i > 0; i--) {
//            final int index = i;
//            service.execute(new Runnable() {
//
//                @SneakyThrows
//                @Override
//                public void run() {
//                    synchronized (this) {
//                        System.out.println(Thread.currentThread().getName() + ":" + index);
//                        Thread.sleep(100);
//                    }
//                }
//            });
//        }

        List<String> classNameList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            classNameList.add(i + "");
        }
        //每6条数据开启一条线程
        int threadSize = 6;
        //总数据条数
        int totalSize = classNameList.size();
        //线程数
        int threadNum = totalSize % threadSize == 0 ? totalSize / threadSize : totalSize / threadSize + 1;

        long start = System.currentTimeMillis();
        System.out.println("线程任务开始执行");
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
        for (final String className : classNameList) {
            service.execute(new Runnable() {

                @SneakyThrows
                @Override
                public void run() {
//                    System.out.println(Thread.currentThread().getName() + "--------------");
                    List<String> studentList = new ArrayList<>();
                    for (int i = 1; i <= 30; i++) {
                        studentList.add(className + "班：学生" + i);
                    }
                    map.put(className, studentList);
//                    Thread.sleep(100);

                }
            });
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();
        System.out.println("线程任务结束执行");
        System.out.println("线程执行花费时间：" + (end - start) + "毫秒");

        System.out.println(map);
    }
}