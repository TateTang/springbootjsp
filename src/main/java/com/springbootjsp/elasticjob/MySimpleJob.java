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
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext context) {
        //格式化格式
        String format = "YYYY-MM-dd hh:mm:ss";
        // DateTimeFormatter.ofPattern方法根据指定的格式输出时间
        String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
        log.info(context.getJobName() + "执行:" +
                "分片参数:" + context.getShardingParameter() +
                ",当前分片项:" + context.getShardingItem() +
                ",time:" + formatDateTime
        );
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
