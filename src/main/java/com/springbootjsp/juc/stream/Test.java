package com.springbootjsp.juc.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Author tangmf
 * @Date 2021/7/12 8:19 下午
 * @Description 一分钟完成，一行代码实现
 * 1、Id 偶数
 * 2、年龄大于25
 * 3、用户名转为大写
 * 4、用户名字母倒序
 * 5、只输出一个用户
 */
public class Test {
    public static void main(String[] args) {
        User u1 = new User(1, "a", 21);
        User u2 = new User(2, "b", 22);
        User u3 = new User(3, "c", 23);
        User u4 = new User(4, "d", 24);
        User u5 = new User(6, "e", 25);
        //集合就是存储
        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);
        //计算交给stream

        //链式编程
        list.stream()
                .filter(u -> u.getId() % 2 == 0)
                .filter(u -> u.getAge() > 23)
                .map(u -> u.getName().toUpperCase())
                .sorted(Comparator.reverseOrder())//倒序
                .limit(1)
                .forEach(System.out::println);
    }
}
