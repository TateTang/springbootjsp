package com.springbootjsp.redisDealyQueue;

import lombok.Data;

import java.io.Serializable;
/**
 * @Author tangmf
 * @Date 2021/7/2 3:04 下午
 * @Description 初始化队列监听
 * 1、通过applictionContext，设置队列名称，
 * 2、启动线程获取队列
 */
@Data
public class TaskDTO implements Serializable {
    private String name;
    private Object body;
}
