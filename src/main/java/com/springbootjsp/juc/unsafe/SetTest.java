package com.springbootjsp.juc.unsafe;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @Author tangmf
 * @Date 2021/7/5 9:29 下午
 * @Description 报错
 * java.util.ConcurrentModificationException 又是并发修改异常
 */
public class SetTest {
    public static void main(String[] args) {
        //List<String> list = new CopyOnWriteArrayList<>();
        Set<String> set = new HashSet<>();//报错
        //1、Collections.synchronizedSet(new HashSet<>())， 不会报错
        //Set<String > set = Collections.synchronizedSet(new HashSet<>());
        //2、new CopyOnWriteArraySet<>();
        //Set<String > set = new CopyOnWriteArraySet<>();//不会报错
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
