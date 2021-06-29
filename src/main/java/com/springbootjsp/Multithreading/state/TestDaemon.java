package com.springbootjsp.Multithreading.state;

/**
 * @Author tangmf
 * @Date 2021/6/28 9:16 下午
 * @Description 守护线程
 */
public class TestDaemon {
    public static void main(String[] args) {
        God god = new God();
        You you = new You();
        Thread t1 = new Thread(god);
        t1.setDaemon(true);//默认false，表示用户线程，true守护线程，正常线程都是用户线程
        t1.start();

        Thread t2 = new Thread(you);//用户线程
        t2.start();
    }
}

//上帝
class God implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("上帝保佑你");
        }
    }
}

//你
class You implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 36500; i++) {
            System.out.println("你一生都开心的活着");
        }
        System.out.println("=========> goodbye!world!========");//
    }
}
