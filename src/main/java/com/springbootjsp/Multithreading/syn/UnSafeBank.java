package com.springbootjsp.Multithreading.syn;

/**
 * @Author tangmf
 * @Date 2021/6/29 8:08 下午
 * @Description 不安全的取钱
 */
public class UnSafeBank {
    public static void main(String[] args) {
        Account account = new Account(100, "结婚基金");
        Drawing you = new Drawing(account, 50, "你");
        Drawing girl = new Drawing(account, 100, "girl");
        you.start();
        girl.start();
    }
}

//账户
class Account {
    int money;//余额
    String name;//卡名

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

//银行：模拟取款
class Drawing extends Thread {
    Account account;//账户
    int drawingMoney;//取了多少钱
    int nowMoney;//剩余多少钱

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    //取钱
    //synchronized 默认锁的是this
    @Override
    public void run() {
        //同步代码块，锁对象
        synchronized (account) {
            //判断有没有钱
            if (account.money - drawingMoney < 0) {
                System.out.println("钱不够了,不能取钱了");
                return;
            }
            try {
                Thread.sleep(100);//放大问题发生性
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //卡内余额 = 卡内余额-取的钱
            account.money = account.money - drawingMoney;
            //手里的钱
            nowMoney = nowMoney + drawingMoney;
            System.out.println(account.name + "余额为：" + account.money);
            //Thread.currentThread().getName()==this.getName;
            System.out.println(this.getName() + "手里的钱为：" + nowMoney);
        }
    }
}
