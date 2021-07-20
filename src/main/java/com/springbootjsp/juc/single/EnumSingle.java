package com.springbootjsp.juc.single;

/**
 * @Author tangmf
 * @Date 2021/7/15 9:18 下午
 * @Description 枚举
 * 本身也是一个class
 */
public enum EnumSingle {
    INSTNANCE;

    private EnumSingle() {
    }

    public EnumSingle getInstnance() {
        return INSTNANCE;
    }
}

