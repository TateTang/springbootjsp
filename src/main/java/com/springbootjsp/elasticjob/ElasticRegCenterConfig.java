package com.springbootjsp.elasticjob;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
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
     * 配置zookeeper注册中心,需要配置init执行初始化逻辑
     */
    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter zkCenter(
            @Value("${regCenter.serverLists}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace) {
        // 1、zk的配置：参数一：zk的地址，如果是集群，每个地址用逗号隔开
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(serverList, namespace);
        zookeeperConfiguration.setMaxRetries(3); //设置重试次数,可设置其他属性
        zookeeperConfiguration.setSessionTimeoutMilliseconds(500000); //设置会话超时时间,尽量大一点,否则项目无法正常启动
        //2、创建协调注册中心CoordinatorRegistryCenter接口，elastic-job提供了一个实现ZookeeperRegistryCenter
        CoordinatorRegistryCenter zookeeperRegistryCenter =
                new ZookeeperRegistryCenter(zookeeperConfiguration);
        // 3 注册中心初始化
        zookeeperRegistryCenter.init();
        return zookeeperRegistryCenter;
    }
}
