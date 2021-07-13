package com.springbootjsp.juc.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @Author tangmf
 * @Date 2021/7/12 8:47 下午
 * @Description 求和计算的任务
 * 非常大的数字
 * 3 6（forkjoin） 9 (strem并行流)
 * 如何使用forkjoin
 * 1、forkjoinPool通过它执行
 * 2、新建一个任务 public void execute(ForkJoinTask<?> task)
 * 3、计算类要继承ForkJoinTask
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    private Long start;//1
    private Long end;//19999999999

    //临界值
    private Long temp = 10000L;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    //计算的方法，返回值就是泛型
    @Override
    protected Long compute() {
        if ((end - start) < temp) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //分支合并计算 forkjoin 递归
            long middle = (start + end) / 2; //中间值
            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            //拆分任务，任务压入线程队列
            task1.fork();//在当前任务正在运行的池中异步执行此任务（如果适用）
            ForkJoinDemo task2 = new ForkJoinDemo(middle + 1, end);
            task2.fork();
            return task1.join() + task2.join();//	join()当 is done返回计算结果。
        }
    }

    //public static void main(String[] args) {
    //    int sum = 0;
    //    //求一个非常大的数字，10亿
    //    //for (int i = 1; i <= 10_0000_0000; i++) {
    //    //    sum += i;
    //    //}
    //    System.out.println(sum);
    //}
}
