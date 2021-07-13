package com.springbootjsp.juc.function;

import java.util.function.Predicate;

/**
 * @Author tangmf
 * @Date 2021/7/8 8:57 下午
 * @Description Predicate
 */
public class PredicateDemo {
    public static void main(String[] args) {
        //断定型接口，有一个输入参数，返回值只能是Boolean
        //匿名内部类，判断字符串是否为空
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String str) {
                return str.isEmpty();
            }
        };
        System.out.println(predicate.test("add"));
    }
}
