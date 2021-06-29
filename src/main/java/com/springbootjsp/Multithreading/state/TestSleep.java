package com.springbootjsp.Multithreading.state;

/**
 * @Author tangmf
 * @Date 2021/6/28 8:18 下午
 * @Description 模拟网络延时
 */
public class TestSleep implements Runnable {
    private int ticketNums = 10;//票数

    @Override
    public void run() {
        while (true) {
            if (ticketNums <= 0) {
                break;//小于不执行
            }
            try {
                Thread.sleep(100);//模拟延时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--->拿到了第" + ticketNums-- + "票");
        }
    }

    public static void main(String[] args) {
        //创建线程对象
        TestSleep sleep = new TestSleep();
        //启动线程
        new Thread(sleep, "小明").start();
        new Thread(sleep, "老师").start();
        new Thread(sleep, "黄牛1").start();
    }
}
