package com.springbootjsp.juc.function;

import java.util.function.Function;

/**
 * @Author tangmf
 * @Date 2021/7/8 8:50 下午
 * @Description 函数式接口
 */
public class FunctionDemo {
    public static void main(String[] args) {
        //匿名内部类
        //Function<String,String> function = new Function<String, String>() {
        //    @Override
        //    public String apply(String str) {
        //        return str;
        //    }
        //};
        //有一个输入参数，一个输出参数
        //lamdba 简化
        Function<String, String> function = (str) -> str;
        System.out.println(function.apply("123"));
    }
}
