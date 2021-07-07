package com.springbootjsp.juc.add;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author tangmf
 * @Date 2021/7/6 9:14 下午
 * @Description 加法计数器
 * 模拟集齐7颗龙珠，召唤神龙
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //集齐7龙珠，召唤神龙
        //线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙成功！");
        });
        for (int i = 1; i <= 7; i++) {
            //lambda能操作变量i吗？不能
            final int temp = i;//解决方案
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"收集"+temp+"颗龙珠");
                try {
                    cyclicBarrier.await();//等待
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
