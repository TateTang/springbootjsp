package com.springbootjsp.juc.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author tangmf
 * @Date 2021/7/2 9:59 上午
 * @Description A 执行完调用B B执行完调用C C 执行完调用A
 */
public class C {
    public static void main(String[] args) {
        Data3 data3 = new Data3();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printC();
            }
        }, "C").start();
    }
}

//资源类
class Data3 {
    Lock lock = new ReentrantLock();//定义一个锁对象

    //三个监视器
    Condition conditionA = lock.newCondition();//condition对象，对象监视器功能
    Condition conditionB = lock.newCondition();//condition对象，对象监视器功能
    Condition conditionC = lock.newCondition();//condition对象，对象监视器功能

    private int number = 1;//A 1 B 2 C 3

    public void printA() {
        lock.lock();//加锁
        try {
            //业务代码，判断-->执行-->通知
            while (number != 1) {
                //A等待
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName() + "===>AAAAAA");
            //唤醒，唤醒指定的人 B
            number = 2;
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }

    }

    public void printB() {
        lock.lock();//加锁
        try {
            //业务代码，判断-->执行-->通知
            while (number != 2) {
                //B等待
                conditionB.await();
            }
            System.out.println(Thread.currentThread().getName() + "===>BBBBBB");
            //唤醒C，唤醒指定的C
            number = 3;
            conditionC.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }
    }

    public void printC() {
        lock.lock();//加锁
        try {
            //业务代码，判断-->执行-->通知
            while (number != 3) {
                //C 等待
                conditionC.await();
            }
            System.out.println(Thread.currentThread().getName() + "===>CCCCCC");
            //唤醒A，唤醒指定的A
            number = 1;
            conditionA.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }
    }
}
