package com.springbootjsp.elasticjob;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author tangmf
 * @Date 2021/6/28 10:42 上午
 * @Description 配置zookeeper注册中心
 */
@Configuration
public class ElasticRegCenterConfig {
    /**
     * 配置zookeeper注册中心
     */
    @Bean(initMethod = "init")  // 需要配置init执行初始化逻辑
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverLists}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace) {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(serverList, namespace);
        zookeeperConfiguration.setMaxRetries(3); //设置重试次数,可设置其他属性
        zookeeperConfiguration.setSessionTimeoutMilliseconds(500000); //设置会话超时时间,尽量大一点,否则项目无法正常启动
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }
}
