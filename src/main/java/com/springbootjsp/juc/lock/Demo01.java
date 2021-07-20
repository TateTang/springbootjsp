package com.springbootjsp.juc.lock;

/**
 * @Author tangmf
 * @Date 2021/7/19 9:34 下午
 * @Description Synchronized
 */
public class Demo01 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            phone.sendSms();
        },"A").start();
        new Thread(()->{
            phone.sendSms();
        },"B").start();
        //发现是顺序执行的，A线程走完后才走B线程
    }
}

class Phone {
    public synchronized void sendSms() {
        System.out.println(Thread.currentThread().getName() + "sms");
        call();//这里也有锁
    }
    public synchronized void call() {
        System.out.println(Thread.currentThread().getName() + "call");
    }
}
