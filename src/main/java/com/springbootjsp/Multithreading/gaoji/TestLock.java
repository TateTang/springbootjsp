package com.springbootjsp.Multithreading.gaoji;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author tangmf
 * @Date 2021/6/29 9:34 下午
 * @Description 测试lock锁
 */
public class TestLock {
    public static void main(String[] args) {
        TestClock2 testClock2 = new TestClock2();


        new Thread(testClock2).start();
        new Thread(testClock2).start();
        new Thread(testClock2).start();
    }
}

class TestClock2 implements Runnable {

    private int ticketNum = 10;

    //定义锁
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();//加锁
            try {
                if (ticketNum > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(ticketNum--);
                } else {
                    break;
                }
            } finally {
                //解锁
                lock.unlock();
            }
        }
    }
}
