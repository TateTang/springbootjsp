package com.springbootjsp.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author tangmf
 * @Date 2021/7/19 8:34 下午
 * @Description
 */
public class CASDemo {
    //CAS 比较并且交换
    public static void main(String[] args) {
        //AtomicInteger atomicInteger = new AtomicInteger(2000);
        //带版本号的原子引用
        //Integer，如果泛型是包装类注意 对象的引用问题，正常情况下，都是一个对象
        AtomicStampedReference<Integer> atomicStampedReference =
                new AtomicStampedReference<>(1, 1);
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();//获得版本号
            System.out.println("A1===>" + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 2,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("A2===>" + atomicStampedReference.getStamp());

            System.out.println(atomicStampedReference.compareAndSet(2, 1,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("A3===>" + atomicStampedReference.getStamp());
        }, "A").start();
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();//获得版本号
            System.out.println("B1===>" + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //版本号已经变了不是最开始的值了，所以修改失败
            System.out.println(atomicStampedReference.compareAndSet(1, 6,
                    stamp, stamp+ 1));
            System.out.println("B2===>" + stamp);

        }, "B").start();


        ////期望的值达到了，那么就更新，否则，不更新，CAS 是CPU 的并发原语
        ////捣乱线程========
        //System.out.println(atomicInteger.compareAndSet(2000, 2001));
        //System.out.println(atomicInteger.get());
        //
        //System.out.println(atomicInteger.compareAndSet(2001, 2000));
        //System.out.println(atomicInteger.get());
        //
        ////期望线程======= 发现能够操作 不知道谁动过了
        ////对于平时写的SQL，乐观锁！
        //System.out.println(atomicInteger.compareAndSet(2000, 6666));
        //System.out.println(atomicInteger.get());
    }
}
