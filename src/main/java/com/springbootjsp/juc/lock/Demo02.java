package com.springbootjsp.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author tangmf
 * @Date 2021/7/19 9:39 下午
 * @Description Lock 锁
 */
public class Demo02 {
    public static void main(String[] args) {
        Phone1 phone = new Phone1();
        new Thread(() -> {
            phone.sendSms();
        }, "A").start();
        new Thread(() -> {
            phone.sendSms();
        }, "B").start();
        //发现是顺序执行的，A线程走完后才走B线程
    }
}

class Phone1 {
    Lock lock = new ReentrantLock();

    public void sendSms() {
        lock.lock();//细节问题，完全两把锁 lock.lock()；lock.unlock()
        //Lock 锁必须配对，否则就会死在里面
        //lock.lock();//无法解锁，加几次锁，就解锁几次
        try {
            System.out.println(Thread.currentThread().getName() + "sms");
            call();//这里也有锁
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            //lock.unlock();
        }
    }

    public void call() {
        lock.lock();//加锁
        try {
            System.out.println(Thread.currentThread().getName() + "call");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }
    }

}
