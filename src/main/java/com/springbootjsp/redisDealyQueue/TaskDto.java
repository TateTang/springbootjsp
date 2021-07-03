package com.springbootjsp.redisDealyQueue;

import lombok.Data;

import java.io.Serializable;
/**
 * @Author tangmf
 * @Date 2021/7/2 3:04 下午
 * @Description 延迟队列Dto定义类
 */
@Data
public class TaskDto implements Serializable {
    private String name;
    private Object body;
}
