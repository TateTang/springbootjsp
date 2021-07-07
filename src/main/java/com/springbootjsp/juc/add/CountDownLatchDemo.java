package com.springbootjsp.juc.add;

import java.util.concurrent.CountDownLatch;

/**
 * @Author tangmf
 * @Date 2021/7/6 9:05 下午
 * @Description 计数器
 * 模拟学生走 关门
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        //倒计时  ，总数是6，必须要执行任务的时候才使用
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "Go out");
                countDownLatch.countDown();//数量减1
            }, String.valueOf(i)).start();
        }
        try {
            countDownLatch.await();//等待计算器归0 再向下执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Close Door");
        //countDownLatch.countDown();//减1

    }
}
