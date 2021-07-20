package com.springbootjsp.juc.single;

/**
 * @Author tangmf
 * @Date 2021/7/15 9:01 下午
 * @Description 静态内部类
 */
public class Holder {
    private Holder() {

    }

    public static Holder getInstance() {
        return InnerClass.HOLDER;
    }

    //静态内部类
    public static class InnerClass {
        private static final Holder HOLDER = new Holder();
    }
}
