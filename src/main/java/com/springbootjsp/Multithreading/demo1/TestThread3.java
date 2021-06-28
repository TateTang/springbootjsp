package com.springbootjsp.Multithreading.demo1;

/**
 * @Author tangmf
 * @Date 2021/6/24 9:29 下午
 * @Description 创建线程方式2 实现Runnable接口，重写run方法，执行线程丢入实现类，调用start
 */
public class TestThread3 implements Runnable {
    @Override
    public void run() {
        //run方法线程体
        for (int i = 0; i < 200; i++) {
            System.out.println("我在看代码---" + i);
        }
    }

    public static void main(String[] args) {
        //创建Runnable接口的实现类对象
        TestThread3 thread3 = new TestThread3();
        new Thread(thread3).start();//创建线程对象，通过线程对象来开启线程，代理，调用start()方法开启线程
        //main线程，主线程
        for (int i = 0; i < 2000; i++) {
            System.out.println("我在看多线程---" + i);
        }
    }
}
