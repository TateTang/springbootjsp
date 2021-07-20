package com.springbootjsp.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author tangmf
 * @Date 2021/6/25 3:42 下午
 * @Description 定时执行，绑定yml中的job boot启动完成后，自动启动
 */
@Slf4j
@Component
@ElasticSimpleJob(
        jobName = "orderSimpleJob",
        cron = "0/5 * * * * ?",   //每5秒执行一次
        //cron = "0 0/10 * * * ? *",   //每10分钟执行一次
        shardingTotalCount = 3,
        shardingItemParameters = "0=A,1=B,2=C",
        overwrite = true
)
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext context) {
        //格式化格式
        String format = "YYYY-MM-dd hh:mm:ss";
        // DateTimeFormatter.ofPattern方法根据指定的格式输出时间
        String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
        log.info("{}===>执行：分片参数：{}，当前分片项：{}，分片总数：{}，time：{}"
                , context.getJobName(), context.getShardingParameter()
                , context.getShardingItem(), context.getShardingTotalCount()
                , formatDateTime);
        //switch (context.getShardingItem()) {
        //    case 0:
        //        System.out.println("分片0定时任务");
        //        break;
        //    case 1:
        //        System.out.println("分片1定时任务");
        //        break;
        //    case 2:
        //        System.out.println("分片2定时任务");
        //        break;
        //}
    }
}
