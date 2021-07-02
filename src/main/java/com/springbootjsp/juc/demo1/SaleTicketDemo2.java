package com.springbootjsp.juc.demo1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author tangmf
 * @Date 2021/7/1 8:40 下午
 * @Description 基本的买票例子
 * 真正的多线程，公司中的多线程开发：降低耦合性
 * 线程就是单独一个资源类，没有任何附属操作！
 * 1、属性，2、方法
 */
public class SaleTicketDemo2 {
    public static void main(String[] args) {
        //并发，多个线程操作同一个资源类
        Ticket2 ticket = new Ticket2();//资源类
        //多线程操作，把资源类丢入线程
        //函数式接口 (参数) -> {代码}

        //并发：
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.sale();//卖票
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.sale();//卖票
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.sale();//卖票
        }, "C").start();
    }
}

//资源类 OOP编程，lock锁
class Ticket2 {
    //属性
    private int number = 50;
    Lock lock = new ReentrantLock();//可重入锁，

    //方法
    //卖票的方式
    public void sale() {
        lock.lock();//加锁

        try {
            //业务代码
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了" + (number--) + "票，剩余" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }
    }
}
