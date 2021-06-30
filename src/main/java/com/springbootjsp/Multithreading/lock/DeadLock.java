package com.springbootjsp.Multithreading.lock;

/**
 * @Author tangmf
 * @Date 2021/6/29 9:00 下午
 * @Description 死锁，多个线程互相抱着对方需要的资源，然后形成僵持
 */
public class DeadLock {
    public static void main(String[] args) {
        MakeUp m1 = new MakeUp(0, "灰姑凉");//先拿口红，1秒钟后拿镜子
        MakeUp m2 = new MakeUp(1, "白雪公主"); //先拿镜子，2秒钟后拿口红
        m1.start();
        m2.start();
    }
}

//口红
class Lipstick {

}

//镜子
class Mirror {

}

//化妆
class MakeUp extends Thread {
    //需要的资源只有一份，用static来保证只有一份
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();
    int choice;//选择
    String girlName;//使用的人

    MakeUp(int choice, String girlName) {
        this.choice = choice;
        this.girlName = girlName;
    }

    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //化妆，互相持有对方的锁，拿到对方的资源
    private void makeup() throws InterruptedException {
        if (choice == 0) {
            //获得口红的锁
            synchronized (lipstick) {
                System.out.println(this.girlName + "获得口红的锁");
                Thread.sleep(1000);
                //造成死锁
                //synchronized (mirror) {//一秒钟后获得镜子的锁
                //    System.out.println(this.girlName + "获得镜子的锁");
                //}
            }
            synchronized (mirror) {//一秒钟后获得镜子的锁
                System.out.println(this.girlName + "获得镜子的锁");
            }
        } else {
            //获得镜子的锁
            synchronized (mirror) {
                System.out.println(this.girlName + "获得镜子的锁");
                Thread.sleep(2000);
                //造成死锁
                //synchronized (lipstick) {//一秒钟后获得口红的锁
                //    System.out.println(this.girlName + "获得口红的锁");
                //}
            }
            synchronized (lipstick) {//一秒钟后获得口红的锁
                System.out.println(this.girlName + "获得口红的锁");
            }
        }
    }
}
