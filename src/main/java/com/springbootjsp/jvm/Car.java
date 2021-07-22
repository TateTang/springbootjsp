package com.springbootjsp.jvm;

/**
 * @Author tangmf
 * @Date 2021/7/20 8:51 下午
 * @Description
 */
public class Car {
    public static void main(String[] args) {
        //类是模板，对象是具体的
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        //对象是具体的
        System.out.println(car1.hashCode());
        System.out.println(car2.hashCode());
        System.out.println(car3.hashCode());
        Class<? extends Car> aClass1 = car1.getClass();
        Class<? extends Car> aClass2 = car2.getClass();
        Class<? extends Car> aClass3 = car3.getClass();
        //类模板一样，一个
        System.out.println(aClass1.hashCode());
        System.out.println(aClass2.hashCode());
        System.out.println(aClass3.hashCode());

        ClassLoader classLoader = aClass1.getClassLoader();
        System.out.println(classLoader);//AppClassLoader 应用程序加载器
        System.out.println(classLoader.getParent());//ExtClassLoader 扩展类加载器
        System.out.println(classLoader.getParent().getParent());//null 1、不存在，2、Java获取不到
    }
}
