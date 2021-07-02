package com.springbootjsp.juc.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author tangmf
 * @Date 2021/7/1 9:43 下午
 * @Description
 * 利用 lock 和condition下的await 和signalALl 生产者消费者问题
 */
public class B {
    public static void main(String[] args) {
        Data2 data2 = new Data2();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data2.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data2.decrement();
            }

        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data2.increment();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data2.decrement();
            }

        }, "D").start();

    }
}

//资源类，独立耦合的 解耦
//等待，业务，通知
class Data2 {
    private int number = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();//取代了对象监视器的作用
    //condition.await();//等待
    //condition.signalAll();//唤醒全部

    //+1
    public void increment() {
        try {
            lock.lock();//加锁
            //业务代码
            while (number != 0) {//0是干活的
                //等待
                condition.await();
            }
            number++;
            //通知其他线程
            System.out.println(Thread.currentThread().getName() + "==>" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }
    }

    //-1
    public void decrement() {
        try {
            lock.lock();//加锁
            //业务代码
            while (number == 0) {//1是干活的
                //等待
                condition.await();
            }
            number--;
            //通知其他线程,-1结束了
            System.out.println(Thread.currentThread().getName() + "==>" + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }
    }
}

