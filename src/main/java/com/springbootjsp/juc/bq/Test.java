package com.springbootjsp.juc.bq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/7 8:22 下午
 * @Description 阻塞队列
 */
public class Test {
    public static void main(String[] args) {
        //BlockingQueue 不是新的东西，
        //test1();
        //test2();
        //test3();
        test4();
    }

    /*
       抛出异常
        */
    public static void test1() {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //System.out.println(blockingQueue.add("d"));//在添加一个，超过队列大小，队列满了，抛出IllegalStateException异常: Queue full
        //FIFO
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());//再弹出一个，队列空了，抛出NoSuchElementException异常
        System.out.println(blockingQueue.element());//查看队首元素

    }

    /*不会抛出异常（有返回值）*/
    public static void test2() {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));//再添加一个，超过队列大小，队列满了，不抛出异常，返回false
        //FIFO
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());//再弹出一个，队列空了，抛出NoSuchElementException异常
        System.out.println(blockingQueue.peek());//查看队首元素

    }

    /*
    阻塞等待 一直等待
     */
    public static void test3() {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            //一直阻塞
            blockingQueue.put("a");
            blockingQueue.put("b");
            blockingQueue.put("c");
            blockingQueue.put("d");//队列没有位置了，一直等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());//队列没有这个元素了，一直等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /*
    超时等待 一直等待
     */
    public static void test4() {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            blockingQueue.offer("a" );
            blockingQueue.offer("b");
            blockingQueue.offer("c");//
            blockingQueue.offer("d", 2, TimeUnit.SECONDS);//等待超过时间退出
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        try {
            blockingQueue.poll(2,TimeUnit.SECONDS);//等待超过2秒钟退出
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
