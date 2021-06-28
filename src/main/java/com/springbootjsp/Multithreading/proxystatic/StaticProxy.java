package com.springbootjsp.Multithreading.proxystatic;

/**
 * @Author tangmf
 * @Date 2021/6/26 8:48 下午
 * @Description 总结：
 * 真实对象和代理对象都要实现同一个接口，🌰：Marray
 * 代理对象代理真实角色：WeddingCompany 代理You
 */
public class StaticProxy {
    public static void main(String[] args) {
        You you = new You();//you要结婚

        //真实对象Runnable
        new Thread(() -> System.out.println("我爱你")).start();
        //让代理对象直接代理真实对象
        //真实对象Marry
        new WedddingCompany(you).HappyMarry();
    }
}

interface Marry {
    //人间四大喜事 久旱逢甘霖 他乡遇故知 洞房花烛夜 金榜题名时
    void HappyMarry();
}

//真实角色
class You implements Marry {

    @Override
    public void HappyMarry() {
        System.out.println("秦老师要结婚了，超开心");
    }
}

//代理角色
class WedddingCompany implements Marry {
    private Marry target;//真实目标角色

    public WedddingCompany(Marry target) {
        this.target = target;
    }

    @Override
    public void HappyMarry() {
        before();
        this.target.HappyMarry();//真实对象
        after();
    }

    private void before() {
        System.out.println("结婚之前，布置现场");
    }

    private void after() {
        System.out.println("结婚之后，收尾款");
    }
}
