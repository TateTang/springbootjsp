package com.springbootjsp.juc.volatilet;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author tangmf
 * @Date 2021/7/13 9:44 下午
 * @Description 不保证原子性
 */
public class VDemo {

    //原子类的Integer 保证原子性操作
    private volatile static AtomicInteger num = new AtomicInteger();
    //private volatile static  int num = 0;
    //public synchronized static void add() {
    //    num++;
    //}//synchronized 可保证
    public  static void add() {
        //num++;//不是一个原子性操作
        num.getAndIncrement();//原子类的+1方法：CAS 方法
    }
    public static void main(String[] args) {
        //理论20000
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }
        while (Thread.activeCount()>2){
            Thread.yield();// 礼让一下
        }
        System.out.println(Thread.currentThread().getName() + " " + num);
    }
}
