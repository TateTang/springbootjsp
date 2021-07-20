package com.springbootjsp.elasticjob;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author tangmf
 * @Date 2021/7/16 11:11 上午
 * @Description 任务监听器
 */
@Component
@Slf4j
public class TaskJobListener implements ElasticJobListener {
    private long beginTime = 0;

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();
        log.info(shardingContexts.getJobName() + "===>开始...");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        log.info(shardingContexts.getJobName() +
                "===>结束...[耗时：" + (endTime - beginTime) + "]");
    }

}
