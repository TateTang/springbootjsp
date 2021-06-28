package com.springbootjsp.Multithreading.demo1;

/**
 * @Author tangmf
 * @Date 2021/6/24 9:02 下午
 * @Description 创建线程继承Thread类
 */
public class TestThread1 extends Thread {
    @Override
    public void run() {
        //run方法线程体
        for (int i = 0; i < 200; i++) {
            System.out.println("我在看代码---" + i);
        }
    }

    public static void main(String[] args) {
        //创建一个线程对象
        TestThread1 thread1 = new TestThread1();
        //调用start()方法开启线程
        thread1.start();
        //main线程，主线程
        for (int i = 0; i < 2000; i++) {
            System.out.println("我在看多线程---" + i);
        }
    }
}
