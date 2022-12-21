package com.example.my_project.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.my_project.entity.Order;
import com.example.my_project.entity.Seckillgoods;
import com.example.my_project.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商品库存表 Mapper 接口
 * </p>
 *
 * @author GongYue
 * @since 2021-12-22
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    int recoveryById(int id);

    List<Stock> selectAll();

    Seckillgoods selectById(@Param("goodsId") int goodsId);
}
