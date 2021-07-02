package com.springbootjsp.juc.pc;

/**
 * @Author tangmf
 * @Date 2021/7/1 9:21 下午
 * @Description 线程之间的通信问题，生产者消费者问题
 * 通知和等待唤醒 wait和notifyAll()
 * 交替执行，A，B同时操作一个变量，num=0
 * A num+1
 * B num-1
 */
public class A {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }

        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }

        }, "D").start();
    }
}

//资源类，独立耦合的 解耦
//等待，业务，通知
class Data {
    private int number = 0;

    //+1
    public synchronized void increment() {
        while (number != 0) {//0是干活的
            //等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number++;
        //通知其他线程
        System.out.println(Thread.currentThread().getName() + "==>" + number);
        this.notifyAll();

    }

    //-1
    public synchronized void decrement() {
        while (number == 0) {//1是干活的
            //等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number--;
        //通知其他线程,-1结束了
        System.out.println(Thread.currentThread().getName() + "==>" + number);
        this.notifyAll();
    }
}
