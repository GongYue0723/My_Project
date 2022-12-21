package com.example.my_project.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.my_project.entity.Order;
import com.example.my_project.entity.Stock;
import com.example.my_project.entity.User;
import com.example.my_project.mq.Producer;
import com.example.my_project.redis.redisConfig;
import com.example.my_project.service.IOrderService;
import com.example.my_project.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class  MQStockServiceImpl {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * Controller传参进来
     * @param orderUser
     * @param stockName
     */
    public void secKill(String orderUser,String stockName, int stockId){
        if (!checkStockName(stockName)){
            return;
        }
        if(!checkOrderUser(orderUser, stockName)){
            return;
        }
        decrRedisStock(orderUser, stockName, stockId, 5);
    }

    /**
     * 确认用户是否参与过秒杀活动
     * @param orderUser
     * @param stockName
     * @return
     */
    synchronized private boolean checkOrderUser(String orderUser, String stockName){
        if(BooleanUtils.isTrue(stringRedisTemplate.hasKey(orderUser))){
            log.warn("当前用户: {}已参与秒杀商品: {}活动", orderUser, stockName);
            return false;
        }
        //将新用户放入缓存随后执行秒杀活动
//        redisTemplate.opsForSet().add(orderUser, stockName);
        //如未支付则进行过期处理
        stringRedisTemplate.opsForValue().set(orderUser, stockName);
        return true;
    }

    /**
     * 确认商品存货
     * @param stockName
     * @return
     */
    private boolean checkStockName(String stockName){
        //确认商品是否预设置库存
        //缓存确认有没有该商品？
        String redisStock = stringRedisTemplate.opsForValue().get(stockName);
//        System.out.println(redisStock);
        if (redisStock == null){
            log.error("商品不合法:{}", redisStock);//该报错表明设置在redis中的秒杀商品已过期(30s)
            return false;
        }
        //商品库存是否为零？
        if (Long.parseLong(redisStock) <= 0){
            log.error("缓存库存为:{}，请重新设置", redisStock);
            return false;
        }
        return true;
//        stringRedisTemplate.hasKey(stockName)
    }

    /**
     * 减少商品库存
     * @param orderUser
     * @param stockName
     */
   synchronized private void decrRedisStock(String orderUser,String stockName, int stockId, int delaytime){
        //分布式锁查询
//        String lockKey = "锁住了" + orderUser;
//        RLock lock = redissonClient.getLock(lockKey);
//        try {
            Order order = new Order();
            //从缓存中减少库存
//        System.out.println(redisConfig.getCache(stockName));
            //返回缓存中还剩的库存进行超卖判断
            String redisStock = stringRedisTemplate.opsForValue().get(stockName);
            //二次确认缓存中的库存
            if (Long.parseLong(redisStock) <= 0) {
                log.info("用户：{}秒杀时商品:{}的库存量没有剩余,秒杀结束", orderUser, stockName);
                return;
            }
            //还有剩余库存进行秒杀
            log.info("秒杀用户: {}; 秒杀商品: {}; 库存有余，进行下单操作", orderUser, stockName);
            //减少缓存内库存
            stringRedisTemplate.opsForValue().decrement(stockName);
            //设置好订单信息，发送出去
            order.setId(stockId);
            order.setRemarks("已支付");
            order.setOrderUser(orderUser);
            order.setOrderName(stockName);
            log.info("1.发送订单消息{}", JSON.toJSONString(order));
            Message message = MessageBuilder.withPayload(order).build();
            rocketMQTemplate.asyncSend("SecKillTopic:*", message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("异步秒杀消息发送成功 sendTime: {}; sendResult: {}", DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").format(LocalDateTime.now()), sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    log.info("异步秒杀失败");
                }
            }, 2000, delaytime);
//            rocketMQTemplate.sendMessageInTransaction("SecKillTopic:*", message, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    log.info("异步秒杀消息发送成功 sendResult {}", sendResult);
//                }
//
//                @Override
//                public void onException(Throwable e) {
//                    log.info("异步秒杀失败 {}", e.toString());
//                }
//            });
//        } finally {
//            log.info("解锁 key:{}", lockKey);
//            lock.unlock();
//        }
    }
}
