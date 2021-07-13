package com.springbootjsp.juc.function;

import java.util.function.Supplier;

/**
 * @Author tangmf
 * @Date 2021/7/8 9:06 下午
 * @Description 供给型接口
 */
public class SupplierDemo {
    public static void main(String[] args) {
        //没有参数，只有返回值
        //Supplier<Integer> supplier = new Supplier<Integer>() {
        //    @Override
        //    public Integer get() {
        //        System.out.println("get()");
        //        return 1024;
        //    }
        //};
        Supplier<Integer> supplier = () -> {
            System.out.println("get()");
            return 1024;
        };
        supplier.get();
    }
}
