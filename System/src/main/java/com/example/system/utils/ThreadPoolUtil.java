package com.example.system.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 线程池工具
 *
 * @author laughlook
 * @date 2022/08/12
 */
public class ThreadPoolUtil {
    static final int MAX_VALUE = 0x7fffffff;

    public static void main(String[] args) {
        newCachedThreadPool(0, MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("cached-thread-%d").build());
        newFixedThreadPool(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("fixed-thread-%d").build());
        newSingleThreadPool(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("single-thread-%d").build());
        newScheduledThreadPool(5, new ThreadFactoryBuilder().setNameFormat("scheduled-thread-%d").build());
    }

    /**
     * 缓存线程池
     *
     * @param corePoolSize    核心池大小
     * @param maximumPoolSize 最大池大小
     * @param keepAliveTime   维持时间
     * @param unit            单位
     * @param workQueue       工作队列
     */
    @SneakyThrows
    public static List<Integer> newCachedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, SynchronousQueue<Runnable> workQueue, ThreadFactory factory) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, factory);
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 1; i <= 100000; i++) {
            int index = i;
            executor.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + index);
                    list.add(index);
                }
            });
        }
        executor.shutdown();
        //判断所有线程是否执行完
        while (true) {
            if (executor.isTerminated()) {
                break;
            }
            Thread.sleep(1000);
        }
        return list;
    }

    /**
     * 定长线程池
     *
     * @param corePoolSize    核心池大小
     * @param maximumPoolSize 最大池大小
     * @param keepAliveTime   维持时间
     * @param unit            单位
     * @param workQueue       工作队列
     * @param factory         工厂
     */
    @SneakyThrows
    public static List<Integer> newFixedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, LinkedBlockingQueue<Runnable> workQueue, ThreadFactory factory) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, factory);
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 1; i <= 100000; i++) {
            int index = i;
            executor.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + index);
                    list.add(index);
                }
            });
        }
        executor.shutdown();
        //判断所有线程是否执行完
        while (true) {
            if (executor.isTerminated()) {
                break;
            }
            Thread.sleep(1000);
        }
        return list;
    }

    /**
     * 单线程池
     *
     * @param corePoolSize    核心池大小
     * @param maximumPoolSize 最大池大小
     * @param keepAliveTime   维持时间
     * @param unit            单位
     * @param workQueue       工作队列
     * @param factory         工厂
     * @return {@link List}<{@link Integer}>
     */
    @SneakyThrows
    public static List<Integer> newSingleThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, LinkedBlockingQueue<Runnable> workQueue, ThreadFactory factory) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, factory);
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 1; i <= 1000; i++) {
            int index = i;
            executor.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + index);
                    list.add(index);
                }
            });
        }
        executor.shutdown();
        //判断所有线程是否执行完
        while (true) {
            if (executor.isTerminated()) {
                break;
            }
            Thread.sleep(1000);
        }
        return list;
    }

    /**
     * 调度线程池
     *
     * @param corePoolSize 核心池大小
     * @return {@link List}<{@link Integer}>
     */
    public static void newScheduledThreadPool(int corePoolSize, ThreadFactory factory) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(corePoolSize, factory);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, 1, 3, TimeUnit.SECONDS);

    }
}

