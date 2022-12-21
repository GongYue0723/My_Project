package com.example.my_project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.my_project.entity.Seckillgoods;
import com.example.my_project.entity.Stock;
import com.example.my_project.dao.StockMapper;
import com.example.my_project.service.IOrderService;
import com.example.my_project.service.IStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品库存表 服务实现类
 * </p>
 *
 * @author GongYue
 * @since 2021-12-22
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {
    @Autowired
    private IStockService iStockService;

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<Stock> selectAll(){
        return stockMapper.selectAll();
    }

    @Override
    public Seckillgoods selectById(int id) {
        return stockMapper.selectById(id);
    }

    @Override
    public void saveStock(Stock stock) {
        iStockService.save(stock);
    }

    //减少库存
    @Override
    public void decrByStock(String name) {
        Stock stock = new Stock();
        stock = iStockService.getOne(new UpdateWrapper<Stock>().lambda().eq(Stock::getName, name));
        System.out.println(stock.getStock());
        int afterNum = Integer.parseInt(stock.getStock())-1;
        System.out.println(afterNum);
        String afterNum2 = String.valueOf(afterNum);
        iStockService.update(new UpdateWrapper<Stock>().lambda().eq(Stock::getName, name).set(Stock::getStock, afterNum2));
    }

}
