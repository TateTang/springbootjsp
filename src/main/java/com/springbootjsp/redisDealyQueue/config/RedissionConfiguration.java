package com.springbootjsp.redisDealyQueue.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author tangmf
 * @Date 2021/7/2 5:41 下午
 * @Description Redisson哨兵模式配置
 */
@Configuration
@ConfigurationProperties(prefix = "redisson")//读取redisson配置
@Data
public class RedissionConfiguration {
    private String masterName;//master名称
    private String schema = "redis://";
    private String[] sentinelAddresses;//地址
    private String password;//密码
    private int timeout = 5000;//超时时间
    private int connectionPoolSize = 64;
    private int connectionMinimumIdleSize = 10;//最小空闲连接量
    private int masterConnectionPoolSize = 250;//主节点连接池大小
    private int slaveConnectionPoolSize = 250;//从节点连接池大小


    /**
     * 哨兵模式
     */
    @Bean
    public RedissonClient redissonSentinel() {
        Config config = new Config();
        // 拼接协议
        String[] sentinelAddressesWithSchema = new String[sentinelAddresses.length];
        for (int i = 0; i < sentinelAddresses.length; i++) {
            sentinelAddressesWithSchema[i] = schema + sentinelAddresses[i];
        }
        SentinelServersConfig serversConfig = config.useSentinelServers()
                .setPingConnectionInterval(timeout)
                .addSentinelAddress(sentinelAddressesWithSchema)
                .setMasterName(masterName)
                .setTimeout(timeout)
                .setMasterConnectionPoolSize(masterConnectionPoolSize)
                .setSlaveConnectionPoolSize(slaveConnectionPoolSize)
                .setMasterConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setSlaveConnectionMinimumIdleSize(connectionMinimumIdleSize);
        if (StrUtil.isNotEmpty(password)) {
            serversConfig.setPassword(password);
        }
        return Redisson.create(config);
    }
}
