package com.example.my_project.mq;

import com.example.my_project.entity.Stock;
import com.example.my_project.redis.redisConfig;
import com.example.my_project.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Producer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IStockService iStockService;

    @PostConstruct
    public void init() {
        //项目启动前的预库存设置
        if (BooleanUtils.isFalse(stringRedisTemplate.hasKey("RTX3090")) && BooleanUtils.isFalse(stringRedisTemplate.hasKey("RTX3090")) && BooleanUtils.isFalse(stringRedisTemplate.hasKey("RTX3090"))) {
            for (int i = 1; i <= 3; i++) {
                Stock stock = iStockService.getById(i);
                stringRedisTemplate.opsForValue().set(stock.getName(), "30");
                log.info("库存预热中 {}", stringRedisTemplate.opsForValue().get(stock.getName()));
            }
        }
    }
}

//        // 4. 发送消息
//        for (int i = 0; i < 10; i++) {
//            //构建实例，第一个参数为topic,第二个参数为tabs,第三个参数为消息体
//            Message message = new Message("MyQuickStartTopic","tabA",("Hello World:" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
//            SendResult result = producer.send(message);
//            System.out.println
//            (result);
//        }
//        //关闭生产者
//        producer.shutdown();