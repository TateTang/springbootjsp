package com.springbootjsp.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/5 8:39 下午
 * @Description
 * 3、打印发短信还是hello （hello普通方法）
 *
 * hello 先执行，发短信后面执行
 * 普通方法先执行
 *
 * 4、两个对象，两个同步方法
 * 先发短信 后打电话 ----两个调用者，不同的对象，锁得对象不一样
 */
public class Test2 {
    public static void main(String[] args) {
        //两个调用者，不同的对象，所得对象不一样
        Phone2 phone1 = new Phone2();//两个对象
        Phone2 phone2 = new Phone2();
        //锁的存在
        new Thread(() -> {
            phone1.sendSms();//发短信
        }, "A").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            //phone1.hello();//普通方法
            phone2.call();//打电话
        }, "B").start();
    }
}

class Phone2 {

    //synchronized 锁的对象是方法的调用者
    //两个方法用的同一个锁，谁先拿到，就谁先执行
    public synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("发短信");
    }

    public synchronized void call() {
        System.out.println("打电话");
    }

    //hello没有锁！非同步方法，不受锁的影响，
    public void hello() {
        System.out.println("hello");
    }
}
