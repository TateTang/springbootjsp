package com.springbootjsp.elasticjob;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author tangmf
 * @Date 2021/7/15 5:43 下午
 * @Description 定时任务配置
 */
@Configuration
public class ElasticJobConfig {
    @Resource
    private CoordinatorRegistryCenter zkCenter;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 配置任务监听器，自定义的
     */
    @Bean
    public ElasticJobListener elasticJobListener() {
        return new TaskJobListener();
    }

    @PostConstruct
    public void initSimpleJob() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ElasticSimpleJob.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?>[] interfaces = instance.getClass().getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (anInterface == SimpleJob.class) {
                    ElasticSimpleJob annotation = instance.getClass().getAnnotation(ElasticSimpleJob.class);
                    String jobName = annotation.jobName();
                    String cron = annotation.cron();
                    boolean overwrite = annotation.overwrite();
                    int shardingTotalCount = annotation.shardingTotalCount();
                    String shardingItemParameters = annotation.shardingItemParameters();

                    //job核心配置
                    JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
                            .newBuilder(jobName, cron, shardingTotalCount)
                            .shardingItemParameters(shardingItemParameters)
                            .build();

                    //job类型配置
                    JobTypeConfiguration jobTypeConfiguration =
                            new SimpleJobConfiguration(jobCoreConfiguration, instance.getClass().getCanonicalName());

                    //job根的配置(LiteJobConfiguration)
                    LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                            .newBuilder(jobTypeConfiguration)
                            .overwrite(overwrite)
                            .build();
                    // 初始化任务
                    new SpringJobScheduler((ElasticJob) instance, zkCenter, liteJobConfiguration).init();
                }
            }
        }
    }
}
