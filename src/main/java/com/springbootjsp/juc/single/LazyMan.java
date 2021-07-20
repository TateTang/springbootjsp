package com.springbootjsp.juc.single;

import java.lang.reflect.Constructor;

/**
 * @Author tangmf
 * @Date 2021/7/15 8:49 下午
 * @Description 懒汉式单例模式
 */
public class LazyMan {
    private LazyMan() {
        synchronized (LazyMan.class) {
            if (lazyMan != null) {
                throw new RuntimeException("不用试图使用反射来破坏单例模式");
            }
        }
        //System.out.println(Thread.currentThread().getName() + "Ok");
    }

    //private static LazyMan lazyMan;
    private volatile static LazyMan lazyMan;//避免指令重排现象返回值出现空的构造方法

    public static LazyMan getInstance() {
        //加锁，双重校验锁，简称DCL 懒汉式单例模式
        if (lazyMan == null) {
            synchronized (LazyMan.class) {
                if (lazyMan == null) {
                    lazyMan = new LazyMan();
                    //不是一个原子性操作
                    /*
                    1、分配内存空间
                    2、执行构造方法，初始化对象
                    3、把这个对象指向这个空间
                    有可能发生指令重排的情况
                    操作 123 132可能操作 A ，
                    第二个线程B 进来，lazyMan 不为空了，直接返回lazyMan
                    此时lazyMna 没有完成构造，为了避免指令重排，lazyMan使用关键字volatile
                     */
                }
            }
        }
        return lazyMan;//
    }

    //单线程下没有问题，多线程下有问题
    //public static void main(String[] args) {
    //    for (int i = 0; i < 10; i++) {
    //        new Thread(() -> {
    //            LazyMan.getInstance();
    //        }).start();
    //    }
    //}

    //反射
    public static void main(String[] args) throws Exception {
        LazyMan instance = LazyMan.getInstance();
        Constructor<LazyMan> constructor = LazyMan.class.getDeclaredConstructor(null);
        constructor.setAccessible(true);//无视私有的构造器
        LazyMan instance2 = constructor.newInstance();
        System.out.println(instance.hashCode());
        System.out.println(instance2.hashCode());
        //反射可以破坏单例，如何解决
    }
}
