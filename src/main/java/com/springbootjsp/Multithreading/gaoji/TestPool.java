package com.springbootjsp.Multithreading.gaoji;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author tangmf
 * @Date 2021/6/30 9:40 下午
 * @Description
 */
public class TestPool {
    public static void main(String[] args) {
        //1、创建线程池 线程池大小10
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());

        //关闭连接
        service.shutdown();
    }
}

class MyThread implements Runnable {

    @Override
    public void run() {
            System.out.println(Thread.currentThread().getName());
    }
}
