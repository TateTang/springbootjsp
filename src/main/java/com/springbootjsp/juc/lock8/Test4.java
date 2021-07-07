package com.springbootjsp.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/5 8:55 下午
 * @Description
 * 7、一个静态的同步方法，一个普通同步方法 ，一个对象，先打印发短信 还是打电话？
 * 打电话--->发短信  静态同步方法锁的是clas模板，普通同步方法锁的是方法调用者，发短信sleep，所以先打电话
 * 8、一个静态的同步方法，一个普通同步方法 ，两个对象，先打印发短信 还是打电话？
 * 打电话--->发短信  静态同步方法锁的是clas模板，普通同步方法锁的是方法调用者，第二个锁
 */
public class Test4 {
    public static void main(String[] args) {
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();
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
            phone2.call();//打电话
        }, "B").start();
    }
}

//只有唯一的一个class对象
class Phone4 {

    //synchronized 锁的对象是方法的调用者
    //两个方法用的同一个锁，谁先拿到，就谁先执行
    //static 静态方法，类一加载就有了 class模板，锁的是一个Class 全局唯一
    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    //普通同步方法 锁的对象是方法的调用者
    public  synchronized void call() {
        System.out.println("打电话");
    }

}
