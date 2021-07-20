package com.springbootjsp.juc.single;

import java.lang.reflect.Constructor;

/**
 * @Author tangmf
 * @Date 2021/7/15 9:21 下午
 * @Description
 */
public class Test {
    public static void main(String[] args) throws Exception {
        //EnumSingle instnance1 = EnumSingle.INSTNANCE;
        //EnumSingle instnance2 = EnumSingle.INSTNANCE;
        //System.out.println(instnance1);
        //System.out.println(instnance2);
        EnumSingle instnance = EnumSingle.INSTNANCE;
        //Constructor<EnumSingle> constructor = EnumSingle.class.getDeclaredConstructor(null);
        //反射不能破坏枚举的单例
        Constructor<EnumSingle> constructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        constructor.setAccessible(true);

        EnumSingle newInstance = constructor.newInstance();
        System.out.println(instnance);
        System.out.println(newInstance);
    }
}
