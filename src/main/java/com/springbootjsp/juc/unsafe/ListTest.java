package com.springbootjsp.juc.unsafe;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author tangmf
 * @Date 2021/7/5 9:04 下午
 * @Description 报错
 * java.util.ConcurrentModificationException 并发修改异常！
 */
public class ListTest {
    public static void main(String[] args) {
        //List<String> list = Arrays.asList("1", "2", "3");
        //list.forEach(System.out::println);//单线程下安全

        //for (int i = 1; i < 10; i++) {
        //    list.add(UUID.randomUUID().toString().substring(0, 5));
        //    System.out.println(list);//没有问题
        //}
        //并发下ArrayList不安全的，
         /*
        解决方案
        1、List<String> list = new Vector<>();
        2、List<String> list = Collections.synchronizedList(new ArrayList<>());
        3、List<String> list = new CopyOnWriteArrayList<>();
         */
        //CopyOnWrite COW 写入时复制，计算机程序设计领域的一种优化策略
        //多线程下调用的时候，list读取的时候，固定的 写入（覆盖）
        //在写入的时候避免覆盖造成数据问题
        /*
        为什么用CopyOnWriteArrayList 不用Vector？
        - 有synchronized 效率较低，cow使用的时lock锁，写入时复制一份，然后再重新赋值
         */
        //读写分离，
        //List<String> list = new ArrayList<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        //List<String> list = new Vector<>();//Vector不报错
        for (int i = 1; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);//报错，
            }, String.valueOf(i)).start();
        }

    }
}
