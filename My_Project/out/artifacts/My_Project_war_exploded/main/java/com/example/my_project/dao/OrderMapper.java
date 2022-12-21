package com.example.my_project.dao;

import com.example.my_project.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 秒杀订单表 Mapper 接口
 * </p>
 *
 * @author GongYue
 * @since 2021-12-15
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    int recoveryById(int id);
}
