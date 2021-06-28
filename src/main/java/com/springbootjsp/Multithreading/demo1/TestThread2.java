package com.springbootjsp.Multithreading.demo1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author tangmf
 * @Date 2021/6/24 9:13 下午
 * @Description 练习Thread 多线程同步下载图片
 */
public class TestThread2 implements Runnable {
    private String url;//图片
    private String name;//文件名

    public TestThread2(String url, String name) {
        this.url = url;
        this.name = name;
    }

    //下载图片线程执行体
    @Override
    public void run() {
        //重写run方法
        WebDownLoader downLoader = new WebDownLoader();
        downLoader.downloader(url, name);
        System.out.println("下载文件名:" + name);
    }

    public static void main(String[] args) {
        TestThread2 t1 = new TestThread2("https://img-blog.csdnimg.cn/img_convert/6da53a3436d52e028e306372d0f4acc0.png", "1.png");
        TestThread2 t2 = new TestThread2("https://img-blog.csdnimg.cn/img_convert/6da53a3436d52e028e306372d0f4acc0.png", "2.png");
        TestThread2 t3 = new TestThread2("https://img-blog.csdnimg.cn/img_convert/6da53a3436d52e028e306372d0f4acc0.png", "3.png");
        //t1.start();
        //t2.start();
        //t3.start();
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        //同时执行
    }
}

//下载器
class WebDownLoader {
    //下载方法
    public void downloader(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常，downloader方法异常");
        }
    }
}
