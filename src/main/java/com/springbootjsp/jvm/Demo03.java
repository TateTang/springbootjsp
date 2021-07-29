package com.springbootjsp.jvm;

/**
 * @Author tangmf
 * @Date 2021/7/22 8:48 下午
 * @Description
 */
public class Demo03 {
    public static void main(String[] args) {
        //返回虚拟机试图使用的最大内存
        long max = Runtime.getRuntime().maxMemory();//字节-->兆
        //返回JVM的总内存
        long total = Runtime.getRuntime().totalMemory();

        System.out.println("max=" + max + "字节\t" + (max / (double) 1024 / 1024) + "MB");
        System.out.println("total=" + total + "字节\t" + (total / (double) 1024 / 1024) + "MB");
        //-Xms1024m -Xmx1024m -XX:+PrintGCDetail
        //305664K+699392K = 1005056k /1024 = 981.5MV
    }
}
