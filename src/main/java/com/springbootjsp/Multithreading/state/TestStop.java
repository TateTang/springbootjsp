package com.springbootjsp.Multithreading.state;

/**
 * @Author tangmf
 * @Date 2021/6/28 8:08 下午
 * @Description 线程停止
 * 1、利用次数。不建议死循环
 * 2、建议使用标识位
 * 3、不使用JDK stop或者destroy方法 停止
 */
public class TestStop implements Runnable {
    //1、设置一个标识位，
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.println("run____Thread" + i++);
        }
    }


    //2、设置一个公开的方法停止线程
    public void stop() {
        this.flag = false;
    }

    public static void main(String[] args) {
        TestStop stop = new TestStop();
        new Thread(stop).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println("main" + i);
            if (i == 900) {
                //调用stop方法，停止线程，主线程一直跑到结束
                stop.stop();
                System.out.println("线程该停止了");
            }
        }
    }
}
