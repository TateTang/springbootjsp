package com.springbootjsp.redisDealyQueue;


/**
 * @Author tangmf
 * @Date 2021/7/2 3:04 下午
 * @Description 延时任务执行器接口，业务实现该接口，可以实现自己的业务逻辑，
 * 需要使用到的延迟队列实现该接口，重写invoke方法
 */
public interface RDelayedQueueListener<T> {
    void invoke(T t);
}
