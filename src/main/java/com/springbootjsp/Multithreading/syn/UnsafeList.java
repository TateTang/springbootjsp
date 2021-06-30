package com.springbootjsp.Multithreading.syn;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author tangmf
 * @Date 2021/6/29 8:23 下午
 * @Description 线程不安全的集合
 */
public class UnsafeList {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            new Thread(() -> {
                //利用同步代码块锁住list这个对象，让其排队
                synchronized (list) {
                    list.add(Thread.currentThread().getName());
                }
            }).start();
            //new Thread(() -> list.add(Thread.currentThread().getName())).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}
