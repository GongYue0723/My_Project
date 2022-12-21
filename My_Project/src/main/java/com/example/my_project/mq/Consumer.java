package com.example.my_project.mq;

import com.alibaba.fastjson.JSON;
import com.example.my_project.entity.Order;
import com.example.my_project.entity.Seckillgoods;
import com.example.my_project.entity.Stock;
import com.example.my_project.redis.redisConfig;
import com.example.my_project.service.impl.OrderServiceImpl;
import com.example.my_project.service.impl.StockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RocketMQMessageListener(topic = "SecKillTopic", consumerGroup = "group-seckill-stocks", consumeThreadMax = 2)
public class Consumer implements RocketMQListener<Order> {
    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    synchronized public void onMessage(Order order) {
        //延迟消息接受，判断缓存中商品状态是否变更为已支付
       String Paystatus = stringRedisTemplate.opsForValue().get(order.getOrderUser());
        System.out.println(Paystatus);
        if (Objects.equals(Paystatus, "已支付")) {
            log.info("商品支付成功");
            String name = order.getOrderName();
            String user = order.getOrderUser();
            String remarks = order.getRemarks();
            //数据库层的库存减少
            stockService.decrByStock(name);
            order.setOrderName(name);
            order.setOrderUser(user);
            order.setRemarks(remarks);
            orderService.save(order);
            //再次将已秒杀成功的用户存入缓存中，以防止用户再次参与

        }

        //商品未支付
        else {
            //状态回溯
            //缓存回溯
            //不进行数据库写入
            stringRedisTemplate.opsForValue().increment(order.getOrderName());
            //用户开启秒杀资格
            stringRedisTemplate.delete(order.getOrderUser());
            log.warn("秒杀商品在规定时间内未支付，请重新抢购");
            return;
        }
        log.info("接收到延迟消息 sendTime: {}; sendResult{}", DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").format(LocalDateTime.now()), order);
    }
    //实例化一个consumer组
//        DefaultMQPushConsumer consumer;
//        consumer = new DefaultMQPushConsumer("my-consumer-group");
//        //设置setNamesrvAddr，同生产者
//        consumer.setNamesrvAddr("10.25.1.65:9876");
//
//        //设置消息读取方式，这里设置的是队尾开始读取
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//
//        //设置订阅主题，第二个参数为过滤tabs的条件，可以写为tabA|tabB过滤Tab,*表示接受所有
//        consumer.subscribe("MyQuickStartTopic", "tabA");
//
//        //注册消息监听
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//                try {
//                    //的到MessageExt
//                    MessageExt messageExt = list.get(0);
//                    String topic = messageExt.getTopic();
//                    String message = new String(messageExt.getBody(),"UTF-8");
//                    int queueId = messageExt.getQueueId();
//                    System.out.println("收到来自topic:" + topic + ", queueId:" + queueId + "的消息：" + message);
//
//                } catch (Exception e) {
//                    //失败，请求稍后重发
//                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//                }
//                //成功
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        }

}
