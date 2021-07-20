package com.springbootjsp.juc.single;

/**
 * @Author tangmf
 * @Date 2021/7/15 8:46 下午
 * @Description 饿汉式单例模式
 * 一上来就加载对象
 * 有可能浪费内存
 */
public class Hungry {
    //可能浪费空间，类加载的时候就创建了对象
    private byte[] data1 = new byte[1025 * 1024];
    private byte[] data2 = new byte[1025 * 1024];
    private byte[] data3 = new byte[1025 * 1024];
    private byte[] data4 = new byte[1025 * 1024];

    private Hungry() {
    }

    private final static Hungry HUNGRY = new Hungry();

    public static Hungry getInstance() {
        return HUNGRY;
    }
}
