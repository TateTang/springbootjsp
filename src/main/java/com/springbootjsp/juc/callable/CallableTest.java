package com.springbootjsp.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author tangmf
 * @Date 2021/7/5 9:50 下午
 * @Description
 */
public class CallableTest {
    public static void main(String[] args) {
        //如何使用
        /*
            Runnable 有一个实现类FutureTask,public FutureTask(Callable<V> callable) 构造方法
        */
        MyThread myThread = new MyThread();
        FutureTask<String> futureTask = new FutureTask<>(myThread);//适配类
        //两个线程，打印几个call 一个，
        new Thread(futureTask, "A").start();//如何使用callable，如何启动callable
        new Thread(futureTask, "B").start();//结果会被缓存，提高效率
        try {
            System.out.println("获取的返回结果是：" + futureTask.get());
            //get方法可能会产生阻塞，耗时的操作，把他放到最后，或者使用异步处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyThread implements Callable<String> {
    @Override
    public String call() {
        System.out.println("call()");

        return "1024";
    }
}
