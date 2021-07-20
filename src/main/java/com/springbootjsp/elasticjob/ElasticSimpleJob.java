package com.springbootjsp.elasticjob;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author tangmf
 * @Date 2021/7/15 5:42 下午
 * @Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ElasticSimpleJob {
    /**
     * 任务名
     */
    String jobName() default "";

    /**
     * 执行时间
     */
    String cron() default "";

    /**
     * 分片总数
     */
    int shardingTotalCount() default 1;

    /**
     * 分片参数配置
     */
    String shardingItemParameters() default "";

    /**
     * 设置本地配置是否可覆盖注册中心配置
     */
    boolean overwrite() default false;


}


