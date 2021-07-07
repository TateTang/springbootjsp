package com.springbootjsp.juc.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author tangmf
 * @Date 2021/7/6 9:34 下午
 * @Description 读写锁
 * 独占锁(写锁) --一次只能被一个线程占有
 * 共享锁(读锁) --多个线程可以同时占有
 * 读-读：可以共存！
 * 读-写：不能共存！
 * 写-写：不能共存！
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        ////有问题 写入中有其他插入
        //Mycache mycache = new Mycache();
        ////写入
        //for (int i = 1; i <= 5; i++) {
        //    final int temp = i;//lambda不能读取到i，所以采用这种方式
        //    new Thread(() -> {
        //        mycache.put(temp + "", temp + "");
        //    }, String.valueOf(i)).start();
        //}
        ////读取
        //for (int i = 1; i <= 5; i++) {
        //    final int temp = i;//lambda不能读取到i，所以采用这种方式
        //    new Thread(() -> {
        //        mycache.get(temp + "");
        //    }, String.valueOf(i)).start();
        //}
        //加读写锁，顺序写入
        MycacheLock mycacheLock = new MycacheLock();
        //写入
        for (int i = 1; i <= 5; i++) {
            final int temp = i;//lambda不能读取到i，所以采用这种方式
            new Thread(() -> {
                mycacheLock.put(temp + "", temp + "");
            }, String.valueOf(i)).start();
        }
        //读取，无序读取
        for (int i = 1; i <= 5; i++) {
            final int temp = i;//lambda不能读取到i，所以采用这种方式
            new Thread(() -> {
                mycacheLock.get(temp + "");
            }, String.valueOf(i)).start();
        }

    }
}

//自定义缓存 ,写入被其他线程插队
class Mycache {
    private volatile Map<String, Object> map = new HashMap<>();//保证原子性

    //存，写，写入的时候
    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写入OK");

    }

    //取，读
    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "读取" + key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取OK");

    }
}

//加锁的
class MycacheLock {
    private volatile Map<String, Object> map = new HashMap<>();//保证原子性

    //读写锁，更加细粒度的控制，锁过程，加锁-->业务代码-->解锁
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //存，写，写入的时候一个线程
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();//写锁，加锁
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();//写锁，解锁
        }
    }

    //取，读，读的时候所有人都可以读
    public void get(String key) {
        readWriteLock.readLock().lock();//读锁，加锁
        try {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();//读锁，解锁
        }
    }
}
