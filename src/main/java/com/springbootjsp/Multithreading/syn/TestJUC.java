package com.springbootjsp.Multithreading.syn;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author tangmf
 * @Date 2021/6/29 8:52 下午
 * @Description JUC java.util.concurrent
 * 测试JUC包安全类型的集合
 */
public class TestJUC {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100000; i++) {
            new Thread(() -> list.add(Thread.currentThread().getName())).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}
