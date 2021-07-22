package com.springbootjsp.jvm;

/**
 * @Author tangmf
 * @Date 2021/7/21 9:09 下午
 * @Description
 */
public class Demo02 {
    public static void main(String[] args) {
        new Demo02().a();
    }
    public static void a(){
        b();
    }
    public static void b(){
        a();
    }
}
