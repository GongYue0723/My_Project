package com.example.my_project.service;

import com.example.my_project.entity.Seckillgoods;
import com.example.my_project.entity.Stock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品库存表 服务类
 * </p>
 *
 * @author GongYue
 * @since 2021-12-22
 */
public interface IStockService extends IService<Stock> {

    void saveStock(Stock stock);

    void decrByStock(String name);

    List<Stock> selectAll();

    Seckillgoods selectById(int id);
}
