package com.springbootjsp.juc.pool;

import java.util.concurrent.*;

/**
 * @Author tangmf
 * @Date 2021/7/7 9:32 下午
 * @Description executors 三大方法
 * AbortPolicy 银行满了，还有人进入，不理会，抛出异常
 * CallerRunsPolicy 哪来的去哪里
 */
public class Demo1 {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());//CPU核数
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();//单个线程
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);//创建固定的线程池的大小
        //ExecutorService threadPool = Executors.newCachedThreadPool();//可伸缩的线程
        //自定义线程池 工作中使用
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5, 3,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(3)
        //,Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());//银行满了，还有人进入，不理会，抛出异常
        //,Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());//哪里来的去哪里，main线程执行
        //,Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());//队列满了 不会抛出异常
        ,Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardOldestPolicy());//队列满了 尝试竞争
        try {
            //for (int i = 1; i <= 5; i++) {//没有触发最大
            //最大承载，队列+maximumPoolSize
            //for (int i = 1; i <= 6; i++) {//触发最大
            for (int i = 1; i <= 20; i++) {//超过最大承载，抛出异常RejectedExecutionException: Task com.springbootjsp.juc.pool
                //使用线程池创建线程
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " OK");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //线程池用完，程序结束，关闭线程
            threadPool.shutdown();
        }
    }
}
