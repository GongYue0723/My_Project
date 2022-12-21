package com.example.my_project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.my_project.dao.OrderMapper;
import com.example.my_project.entity.Order;
import com.example.my_project.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀订单表 服务实现类
 * </p>
 *
 * @author GongYue
 * @since 2021-12-15
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private IOrderService iOrderService;

    @Override
    public int recoveryById (int id){
        return recoveryById(id);
    }

    @Override
    public void saveOrder(Order order) {
        iOrderService.save(order);
    }

    @Override
    public void deleteOrder(String orderName) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper();
        //调用baseMapper的delete方法将delFlag 变为1；
        iOrderService.remove(queryWrapper.lambda().eq(Order::getOrderName, orderName));
    }

}
