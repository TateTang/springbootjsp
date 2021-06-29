package com.springbootjsp.Multithreading.state;

/**
 * @Author tangmf
 * @Date 2021/6/28 8:48 下午
 * @Description 测试线程的状态
 */
public class TestState {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("//////");
        });
        //观测状态
        Thread.State state = thread.getState();
        System.out.println(state);//New

        //观测启动线程后
        thread.start();//启动线程
        state = thread.getState();
        System.out.println(state);//run

        while (state != Thread.State.TERMINATED) {
            try {
                //只要线程不终止，一直输出状态
                Thread.sleep(100);
                state = thread.getState();//更新线程状态
                System.out.println(state);//输出状态
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread.start();//一旦进入死亡状态，不能再次启动


    }
}
