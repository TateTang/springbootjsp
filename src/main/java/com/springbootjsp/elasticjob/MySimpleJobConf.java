package com.springbootjsp.elasticjob;

import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author tangmf
 * @Date 2021/6/28 10:44 上午
 * @Description 配置MySimpleJob
 */
@Configuration
public class MySimpleJobConf {
    @Autowired
    private ZookeeperRegistryCenter regCenter;
    @Autowired
    private MySimpleJob mySimpleJob;

    /**
     * 配置任务调度: 参数:  任务
     * zk注册中心
     * 任务详情
     */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(@Value("${mySimpleJob.cron}") final String cron,  //yml注入
                                           @Value("${mySimpleJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${mySimpleJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(mySimpleJob, regCenter,
                ElasticJobUtils.getSimpleJobConfiguration(
                        mySimpleJob.getClass(),
                        cron,
                        shardingTotalCount,
                        shardingItemParameters)
                //,new MyElasticJobListener() 可配置监听器
        );
    }
}
