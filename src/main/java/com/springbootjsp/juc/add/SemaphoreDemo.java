package com.springbootjsp.juc.add;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/6 9:22 下午
 * @Description 许可证
 * 模拟抢车位
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //3个停车位，6个车
        //线程数量，停车位！有限的情况下进行限流
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                // acquire()得到
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); // release 释放
                }
            }, String.valueOf(i)).start();
        }
    }
}