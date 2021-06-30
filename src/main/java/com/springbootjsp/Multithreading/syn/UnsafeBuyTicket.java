package com.springbootjsp.Multithreading.syn;

/**
 * @Author tangmf
 * @Date 2021/6/29 7:59 下午
 * @Description 不安全的买票
 * 线程不安全，有负数
 */
public class UnsafeBuyTicket {
    public static void main(String[] args) {
        BuyTicket station = new BuyTicket();

        new Thread(station,"小明").start();
        new Thread(station,"小王").start();
        new Thread(station,"黄牛党").start();
    }
}

class BuyTicket implements Runnable {
    //票
    private int ticketNums = 10;
    private boolean flag = true;//线程停止

    @Override
    public void run() {
        //买票
        while (flag) {
            buy();
        }
    }

    //synchronized 同步方法，加锁，锁的是this，保证线程安全
    public synchronized void buy() {
        //判断是否有票
        if (ticketNums <= 0) {
            flag = false;
            return;
        }

        //模拟延时
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //买票
        System.out.println(Thread.currentThread().getName() + "拿到" + ticketNums--);
    }
}
