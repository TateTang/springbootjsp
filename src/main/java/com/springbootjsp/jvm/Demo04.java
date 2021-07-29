package com.springbootjsp.jvm;

import java.util.ArrayList;

/**
 * @Author tangmf
 * @Date 2021/7/22 9:38 下午
 * @Description
 */
//dump文件 -Xms1m -Xmx8m -XX:+HeapDumpOnOutOfMemoryError
public class Demo04 {
    byte[] array = new byte[1 * 1024 * 1024];//1m

    public static void main(String[] args) {
        ArrayList<Demo04> list = new ArrayList<>();
        int count = 0;
        try {
            while (true) {
                list.add(new Demo04());//问题所在
                count = count + 1;
            }
        } catch (Exception e) {
            System.out.println("count=" + count);
            e.printStackTrace();
        }
    }
}
