package com.springbootjsp.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author tangmf
 * @Date 2021/7/19 9:47 下午
 * @Description
 */
public class SpinLockDemo {
    // Thread 没有null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //加锁
    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "====>myLock");
        //自旋锁，是null值，设置成期望的thread
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    //解锁
    public void myUnLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "====>myUnLock");
        atomicReference.compareAndSet(thread, null);//是期望的线程，置为空
    }
}

class SpinLockDemoTest {
    public static void main(String[] args) {
        //Lock lock = new ReentrantLock();

        SpinLockDemo lock = new SpinLockDemo();
        new Thread(() -> {
            lock.myLock();//加锁
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.myUnLock();
            }
        }, "T1").start();
        //保证T1线程先拿到锁
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            lock.myLock();//加锁
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.myUnLock();
            }
        }, "T2").start();
    }
}
