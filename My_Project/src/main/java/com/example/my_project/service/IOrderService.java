package com.example.my_project.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.my_project.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 秒杀订单表 服务类
 * </p>
 *
 * @author GongYue
 * @since 2021-12-15
 */
public interface IOrderService extends IService<Order> {
    int recoveryById(int id);

    /*
    保存订单
     */
    void saveOrder(Order order);

    /*
    删除订单(根据订单名称)
     */
    void deleteOrder(String orderName);
}
