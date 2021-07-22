package com.springbootjsp.jvm;

/**
 * @Author tangmf
 * @Date 2021/7/20 9:13 下午
 * @Description
 */
public class Student {
    //双亲委派机制：安全
    //APP-->EXC-->BOOT（最终执行)
    //BOOT EXC APP
    @Override
    public String toString() {
        return "hello";
    }

    public static void main(String[] args) {
        Student student = new Student();
        System.out.println(student.getClass().getClassLoader());//AppClassLoader
    }
}
