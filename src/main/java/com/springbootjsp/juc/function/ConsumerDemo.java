package com.springbootjsp.juc.function;

import java.util.function.Consumer;

/**
 * @Author tangmf
 * @Date 2021/7/8 9:03 下午
 * @Description
 */
public class ConsumerDemo {
    public static void main(String[] args) {
        //消费者接口只有输入，没有返回值
        //Consumer<String> consumer = new Consumer<String>() {
        //    @Override
        //    public void accept(String  str) {
        //        System.out.println(str);
        //    }
        //};
        //lambda 简化
        Consumer<String> consumer = System.out::println;
        consumer.accept("test");
    }

}
