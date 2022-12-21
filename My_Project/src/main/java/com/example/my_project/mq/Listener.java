package com.example.my_project.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.my_project.entity.Stock;
import com.example.my_project.redis.redisConfig;
import com.example.my_project.service.IStockService;
import com.example.my_project.service.impl.MQStockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@RocketMQTransactionListener//次注解用于执行LocalTransaction的接口,后转换为producer
//消息发出后的监听事项
public class Listener implements RocketMQLocalTransactionListener {

    //用于储存当前线程对应的状态
//    private CocurrentStatusMap<String, Integer>  localTrans = new ConcurrentHashMapMap<>();
    @Autowired
    private MQStockServiceImpl mqStockService;

    @Autowired
    private IStockService iStockService;

    /**
     * 发送prepare消息成功后调用该方法用于执行本地事务
     * @param msg:回传的消息，利用transactionId即可获取到该消息的唯一Id
     * @param arg:调用send方式指定的传参，当send时候若有额外的参数可以传递到send方法中，这里获取到
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(org.springframework.messaging.Message msg, Object arg) {
        try{
            String message = new String((byte[]) msg.getPayload());
            log.info("2.解析到消息{}", message);
            Stock stock = JSON.parseObject(message, Stock.class);
            log.info("3.事务回调{}", stock);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    /**
     * 事务回查
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(org.springframework.messaging.Message msg) {
        log.info("事务状态回查");
        String message = new String((byte[]) msg.getPayload());
        log.info("2.解析到的消息{}", message);
        int count = (int) iStockService.count(new QueryWrapper<Stock>().lambda().eq(Stock::getRemarks, "已下单"));
        //代表业务层已实现
        if (count > 0){
            return RocketMQLocalTransactionState.COMMIT;
        }
        else{
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }


}
