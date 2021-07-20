package com.springbootjsp.juc.volatilet;

import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/13 9:35 下午
 * @Description
 */
public class JMMDemo {
    /* 程序并没有停止，主线程修改了 num的值，但是线程1不能及时可见并没有写回和监听到
     * 需要让线程1 知道主线程中的num 发生了变化
     * */
    //private  static int num = 0;

    //程序不会死循环了
    private volatile static int num = 0;

    public static void main(String[] args) {//main 主线程
        new Thread(() -> {
            //线程1
            while (num == 0) {
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 1;

        System.out.println("num==>" + num);
    }
}
