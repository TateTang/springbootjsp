package com.springbootjsp.juc.unsafe;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author tangmf
 * @Date 2021/7/5 9:38 下午
 * @Description 报错
 * ConcurrentModificationException 并发修改异常
 */
public class HashMapTest {
    public static void main(String[] args) {
        //map这样用的吗？ 不是
        // 默认等价与什么？默认等价于 new HashMap<>(16,0.75)
        //Map<String, Object> map = new HashMap<>();

        //加载因子，初始容量
        //第一个方案
        //Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
        //第二个方案
        Map<String, Object> map = new ConcurrentHashMap<>();
        for (int i = 0; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
