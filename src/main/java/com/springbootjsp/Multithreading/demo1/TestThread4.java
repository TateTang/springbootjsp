package com.springbootjsp.Multithreading.demo1;

/**
 * @Author tangmf
 * @Date 2021/6/24 9:41 下午
 * @Description 多个线程同时操作一个对象，买火车票
 * 多个线程操作同一个资源的情况下，数据紊乱，线程不安全
 */
public class TestThread4 implements Runnable {
    private int ticketNums = 10;//票数

    @Override
    public void run() {
        while (true) {
            if (ticketNums <= 0) {
                break;//小于不执行
            }
            try {
                Thread.sleep(200);//模拟延时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--->拿到了第" + ticketNums-- + "票");
        }
    }

    public static void main(String[] args) {
        //创建线程对象
        TestThread4 ticket = new TestThread4();
        //启动线程
        new Thread(ticket, "小明").start();
        new Thread(ticket, "老师").start();
        new Thread(ticket, "黄牛1").start();
        new Thread(ticket, "黄牛2").start();
        new Thread(ticket, "黄牛3").start();
        new Thread(ticket, "黄牛4").start();
        new Thread(ticket, "黄牛5").start();
    }
}
