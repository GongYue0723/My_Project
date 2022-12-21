package com.example.my_project.redis;


import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class redisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

//    /**
//     * 缓存基本的对象，Integer、String、实体类等
//     *无时间限制
//     * @param key
//     * @param value
//     */
//    public void setCache(String key, T value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//
//    /**
//     * 缓存基本的对象，Integer、String、实体类等,具有时间限制
//     *
//     * @param key
//     * @param value
//     * @param timeout@
//     * @param unit@时间单位
//     */
//    public void setCache(String key, T value, long timeout, TimeUnit unit) {
//        redisTemplate.opsForValue().set(key, value, timeout, unit);
//    }
//
//
//    /**
//     * 获得缓存的基本对象
//     *
//     * @param key
//     * @return
//     */
//    public T getCache(String key) {
//        return null == key ? null : redisTemplate.opsForValue().get(key);
//    }
//
//
//    /**
//     * 缓存List数据
//     *
//     * @param key
//     * @param dataList
//     */
//    public void setCacheList(String key, List<T> dataList) {
//        ListOperations<String, T> listOperation = redisTemplate.opsForList();
//        if (null != dataList) {
//            int size = dataList.size();
//            for (int i = 0; i < size; i++) {
//                listOperation.rightPush(key, dataList.get(i));
//            }
//        }
//    }
//
//    /**
//     * 获得缓存的list对象
//     *
//     * @param key
//     * @return
//     */
//    public List<T> getCacheList(String key) {
//        List<T> dataList = new ArrayList<T>();
//        ListOperations<String, T> listOperation = redisTemplate.opsForList();
//        Long size = listOperation.size(key);
//        for (int i = 0; i < size; i++) {
//            dataList.add((T) listOperation.leftPop(key));
//        }
//        return dataList;
//    }
//
//
//    /**
//     * 删除缓存中的key
//     *
//     * @param key
//     */
//    public void delete(String key) {
//        redisTemplate.delete(key);
//    }
//
//
//    /**
//     * 批量删除缓存中的key
//     *
//     * @param keys
//     */
//    public void delete(Collection keys) {
//        redisTemplate.delete(keys);
//    }
//
//
//    /**
//     * 判断缓存中是否存在key
//     *
//     * @param key
//     * @return
//     */
//    public boolean exists(String key) {
//        return redisTemplate.hasKey(key);
//    }
//
//    /**
//     * 增加数量
//     *
//     * @param key
//     * @param amount
//     * @return
//     */
//    public long increment(String key, Integer amount) {
//        return redisTemplate.opsForValue().increment(key, amount);
//    }
//
//    public long decrement(String key){
//        return redisTemplate.opsForValue().decrement(key);
//    }
}
