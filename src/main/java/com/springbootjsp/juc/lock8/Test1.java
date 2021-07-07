package com.springbootjsp.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/5 8:29 下午
 * @Description 1、标准情况下，两个线程先打印发短信 还是打电话
 * 1、发短信  2、打电话  sendSms()先执行是错误的，
 *
 * 2、synchronized 延迟
 * sendSms 延迟4秒，还是 发短信在前，打电话在后 因为锁的存在
 */
public class Test1 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        //锁的存在
        new Thread(() -> {
            phone.sendSms();//发短信
        }, "A").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            phone.call();//打电话
        }, "B").start();
    }
}

class Phone {

    //synchronized 锁的对象是方法的调用者
    //两个方法用的同一个锁，谁先拿到，就谁先执行
    public synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("发短信");
    }

    public synchronized void call() {
        System.out.println("打电话");
    }
}
