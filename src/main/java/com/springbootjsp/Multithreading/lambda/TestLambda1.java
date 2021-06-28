package com.springbootjsp.Multithreading.lambda;

/**
 * @Author tangmf
 * @Date 2021/6/26 9:17 下午
 * @Description lambda表达式测试
 */
public class TestLambda1 {
    //3、静态内部类
    static class Like2 implements ILike {
        @Override
        public void lambda() {
            System.out.println("I like lambda2");
        }
    }

    public static void main(String[] args) {
        //以前的写法
        new Like().lambda();
        new Like2().lambda();

        //4、局部内部类
        class Like3 implements ILike {
            @Override
            public void lambda() {
                System.out.println("I like lambda3");
            }
        }
        new Like3().lambda();
        //5、匿名内部类,没有类的名称，必须借助接口或者父类
        ILike like;
        like = new ILike() {
            @Override
            public void lambda() {
                System.out.println("I like lambda4");
            }
        };
        like.lambda();

        //6、lambda简化
        like = () -> System.out.println("I like lambda5");
        like.lambda();

        //7.lambda简化
        ILove love = (int a) -> {
            System.out.println("i love you " + a);
        };
        love.love(520);
        //7.1 lambda简化1、参数类型
        love = (a) -> System.out.println("i love you " + a);
        love.love(521);
        //7.2 lambda简化2、简化括号
        love = a -> System.out.println("i love you " + a);
        love.love(522);
        //7.2 lambda简化3、去掉花括号
        love = a -> System.out.println("i love you " + a);
        love.love(522);
        //总结：lambda表达式只能的有一行代码的情况下，才能简化成为一行，如果一行，就是代码块{}包括
        //必须是接口为函数式接口，只包含一个唯一一个抽象方法
    }
}

//1、定义一个函数式接口，就是只包含唯一一个抽象方法，
interface ILike {
    void lambda();
}

//2、实现类
class Like implements ILike {
    @Override
    public void lambda() {
        System.out.println("I like lambda");
    }
}

interface ILove {
    void love(int a);
}
