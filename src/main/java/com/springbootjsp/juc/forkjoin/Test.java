package com.springbootjsp.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @Author tangmf
 * @Date 2021/7/12 9:05 下午
 * @Description
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //test1();//446:10_0000_0000L 4465:100_0000_0000L
        //test2();//315:10_0000_0000L  2492:100_0000_0000L
        test3();//214:10_0000_0000L  1291:100_0000_0000L
    }

    //普通的计算
    public static void test1() {
        long sum = 0L;
        long start = System.currentTimeMillis();
        for (long i = 1L; i <= 10_0000_0000L; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum=" + sum + " 时间：" + (end - start));
    }

    //forkjoin计算
    public static void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = new ForkJoinDemo(0L, 10_0000_0000L);
        //forkJoinPool.execute(forkJoinTask);//执行任务，没有返回
        ForkJoinTask<Long> submit = forkJoinPool.submit(forkJoinTask);//提交任务，有返回值
        Long sum = submit.get();//等待计算完成，然后检索其结果。
        long end = System.currentTimeMillis();
        System.out.println("sum=" + sum + " 时间：" + (end - start));
    }

    //stream并行流计算
    public static void test3() {
        long start = System.currentTimeMillis();
        //rangeClosed(long startInclusive, long endInclusive) 返回有序顺序 LongStream从 startInclusive （含）至 endInclusive通过的递增步长（含） 1
        //parallel()返回平行的等效流。
        //reduce(LongBinaryOperator op) 使用associative累积功能对此流的元素执行reduction ，并返回描述减小值的OptionalLong （如果有）
        long sum = LongStream.rangeClosed(0L, 10_0000_0000L)
                .parallel().reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println("sum=" + sum + " 时间：" + (end - start));
    }
}

