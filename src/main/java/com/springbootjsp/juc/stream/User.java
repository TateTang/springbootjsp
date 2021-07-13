package com.springbootjsp.juc.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author tangmf
 * @Date 2021/7/12 8:14 下午
 * @Description
 * 有参，无参构造，set get 方法
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
}
