package com.springbootjsp.redis;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskDTO implements Serializable {
    private String name;
    private Object body;
}