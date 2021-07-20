package com.springbootjsp.juc.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author tangmf
 * @Date 2021/7/12 9:42 下午
 * @Description 异步调用 CompletableFuture
 * 异步执行
 * 失败回调
 * 成功回调
 */
public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //发起一个请求，没有返回值的异步回调
        //CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
        //    try {
        //        TimeUnit.SECONDS.sleep(2);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //    System.out.println(Thread.currentThread().getName() + "runAsync==>void");
        //});
        //System.out.println("111");
        //completableFuture.get(); //阻塞获取执行结果，有时间会等待

        //有返回值的异步回调supplyAsync
        //成功，失败：
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "supplyAsync==>Integer");
            int i = 10 / 0;
            return 1024;
        });//有返回值的接口

        //whenComplete 成功  exceptionally 失败
        completableFuture.whenComplete((t, u) -> {
            System.out.println("t=>" + t);//t 正常的返回结果
            System.out.println("u=>" + u);//u 错误信息
        }).exceptionally((e) -> {
            System.out.println("e=>" + e.getMessage());
            e.printStackTrace();
            return 233; // 阔以获取到错误的返回结果
        }).get();
    }
}
