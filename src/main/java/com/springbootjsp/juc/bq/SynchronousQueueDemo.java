package com.springbootjsp.juc.bq;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/7 9:05 下午
 * @Description 同步队列demo
 * 其他的blockingqueue 不一样，同步队列不存储元素，
 * 只要put了一个元素。必须要先使用take取出来，否则不能put进去
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        //同步队列
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "put 1");
                synchronousQueue.put("1");

                System.out.println(Thread.currentThread().getName() + "put 2");
                synchronousQueue.put("2");

                System.out.println(Thread.currentThread().getName() + "put 3");
                synchronousQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "T1").start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "值===>" + synchronousQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "值===>" + synchronousQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "值===>" + synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T2").start();
    }
}
