package com.springbootjsp.Multithreading.demo2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;

/**
 * @Author tangmf
 * @Date 2021/6/26 8:36 下午
 * @Description 线程创建方式3：实现Callable接口
 */
public class TestCallable implements Callable<Boolean> {
    private String url;//图片
    private String name;//文件名

    public TestCallable(String url, String name) {
        this.url = url;
        this.name = name;
    }


    @Override
    public Boolean call() {
        //重写call方法
        WebDownLoader downLoader = new WebDownLoader();
        downLoader.downloader(url, name);
        System.out.println("下载文件名:" + name);
        return true;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestCallable t1 = new TestCallable("https://img-blog.csdnimg.cn/img_convert/6da53a3436d52e028e306372d0f4acc0.png", "1.png");
        TestCallable t2 = new TestCallable("https://img-blog.csdnimg.cn/img_convert/6da53a3436d52e028e306372d0f4acc0.png", "2.png");
        TestCallable t3 = new TestCallable("https://img-blog.csdnimg.cn/img_convert/6da53a3436d52e028e306372d0f4acc0.png", "3.png");
        // 创建执行服务：ExecutorService ser = ExecutorService.newFixedThreadPool(1);
        ExecutorService ser = Executors.newFixedThreadPool(3);
        // 提交执行：Future<Boolean> result1= ser.submit(1)
        Future<Boolean> r1 = ser.submit(t1);
        Future<Boolean> r2 = ser.submit(t2);
        Future<Boolean> r3 = ser.submit(t3);
        // 获取结果：boolean r1 = result1.get()
        boolean rs1 = r1.get();
        boolean rs2 = r2.get();
        boolean rs3 = r3.get();
        System.out.println("rs1"+rs1);
        System.out.println("rs2"+rs2);
        System.out.println("rs3"+rs3);
        // 关闭服务：ser.shutdownNow();
        ser.shutdownNow();
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

