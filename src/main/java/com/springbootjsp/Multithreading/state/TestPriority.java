package com.springbootjsp.Multithreading.state;

/**
 * @Author tangmf
 * @Date 2021/6/28 9:01 下午
 * @Description 测试线程优先级
 */
public class TestPriority extends Thread {
    public static void main(String[] args) {
        //主线程默认优先级
        System.out.println(Thread.currentThread().getName() +
                "---->" + Thread.currentThread().getPriority());
        MyPriority priority = new MyPriority();
        Thread t1 = new Thread(priority);
        Thread t2 = new Thread(priority);
        Thread t3 = new Thread(priority);
        Thread t4 = new Thread(priority);
        Thread t5 = new Thread(priority);
        Thread t6 = new Thread(priority);
        //先设置优先级，再启动
        t1.start();

        t2.setPriority(1);
        t2.start();

        t3.setPriority(4);
        t3.start();


        t4.setPriority(Thread.MAX_PRIORITY);//最大优先级 10
        t4.start();

        t5.setPriority(8);//抛出异常 源码
        t5.start();

        t6.setPriority(7);//抛出异常
        t6.start();
    }
}

class MyPriority implements Runnable {

    @Override
    public void run() {
        //打印线程优先级
        System.out.println(Thread.currentThread().getName() +
                "---->" + Thread.currentThread().getPriority());
    }
}
