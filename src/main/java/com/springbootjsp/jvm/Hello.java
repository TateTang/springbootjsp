package com.springbootjsp.jvm;

import java.util.Random;

/**
 * @Author tangmf
 * @Date 2021/7/22 9:19 下午
 * @Description
 */
public class Hello {
    public static void main(String[] args) {
        String str = "dsaffasdfdasfd";
        while (true) {
            str += str + new Random().nextInt(88888888) + new Random().nextInt(999999999);
        }
    }
}
