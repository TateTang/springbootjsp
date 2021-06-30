package com.springbootjsp.Multithreading.gaoji;

/**
 * @Author tangmf
 * @Date 2021/6/30 8:31 下午
 * @Description 生产者消费者模型-通信问题：管程法解决
 */
//生产者，消费者，缓冲区，产品
public class TestPC {
    public static void main(String[] args) {
        SyncContainer container = new SyncContainer();
        new Productor(container).start();
        new Consumer(container).start();
    }
}

//生产者
class Productor extends Thread {
    SyncContainer container;

    public Productor(SyncContainer container) {
        this.container = container;
    }


    @Override
    public void run() {
        //生产
        for (int i = 0; i < 100; i++) {
            container.push(new Chicken(i));//放入缓冲区
            System.out.println("生产了" + i + "只鸡");
        }
    }
}

//消费者
class Consumer extends Thread {
    SyncContainer container;

    public Consumer(SyncContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        //消费
        for (int i = 0; i < 100; i++) {
            System.out.println("消费了-->第" + container.pop().id + "只鸡");
        }
    }
}

//产品
class Chicken {
    int id;//产品编号

    public Chicken(int id) {
        this.id = id;
    }
}

//缓冲区
class SyncContainer {
    //容器大小
    Chicken[] chickens = new Chicken[10];
    //容器计数器
    int count = 0;

    //生产者放入产品
    public synchronized void push(Chicken chicken) {
        //容器满了，需要等待消费者消费，如果没有满，丢入产品
        if (count == chickens.length) {
            //通知消费者消费，生产者等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        chickens[count] = chicken;
        count++;
        //可以通知消费者消费了
        this.notifyAll();
    }

    //消费者消费产品
    public synchronized Chicken pop() {
        //判断能否消费
        if (count == 0) {
            //等待生产者生产，消费者等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //可以消费，count--
        count--;
        //产品消费了，通知生产者
        Chicken chicken = chickens[count];
        this.notifyAll();
        return chicken;
    }
}
