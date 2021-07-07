package com.springbootjsp.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/5 8:45 下午
 * @Description 5、增加两个静态的同步方法 只有一个对象，先打印发短信还是打电话？
 * 发短信 后打电话
 * 一加载就有了 class模板，锁的是一个Class 全局唯一
 * <p>
 * 6、两个对象 增加两个静态同步方法，先打印发短信还是打电话？
 * 锁的class 对象，两个对象的class类模板只有一个 phone3
 */
public class Test3 {
    public static void main(String[] args) {
        //两个对象的class类模板只有一个，static 锁的是Class
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();
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
class Phone3 {

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

    public static synchronized void call() {
        System.out.println("打电话");
    }

}
