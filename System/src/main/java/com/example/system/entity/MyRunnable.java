package com.example.system.entity;

import lombok.SneakyThrows;

/**
 * MyRunnable
 *
 * @author laughlook
 * @date 2022/07/21
 */
public class MyRunnable implements Runnable {
    private int a = 100;

    @Override
    public void run() {
        while (a > 0) {
            sell();
        }
    }

    @SneakyThrows
    private synchronized void sell() {
        this.notify();
        if (a > 0) {
            Thread.sleep(10);
            System.out.println(Thread.currentThread().getName() + ":" + a--);
            this.wait();
        }

    }
}