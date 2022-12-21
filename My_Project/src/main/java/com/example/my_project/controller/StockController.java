package com.example.my_project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.my_project.entity.Seckillgoods;
import com.example.my_project.entity.Stock;
import com.example.my_project.service.IOrderService;
import com.example.my_project.service.IStockService;
import com.example.my_project.service.impl.MQStockServiceImpl;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * <p>
 * 商品库存表 前端控制器
 * </p>
 *
 * @author GongYue
 * @since 2021-12-22
 */
@Controller
public class StockController {
    @Autowired
    private IStockService stockService;

    @Autowired
    private MQStockServiceImpl mqStockService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IOrderService iOrderService;
    /**
     * 使用redis+消息队列进行秒杀实现
     * @param stockName 商品名称
     * @return String
     */
    @RequestMapping("/seckill")
    @ResponseBody
    synchronized public Map<String, Object> secKillResult(@RequestParam("orderUser") String id, @RequestParam("stockName") String stockName, @RequestParam("stockId") int stockId){
        Map<String, Object> map = new HashMap<>();
        //查看该用户是否抢购过该商品
        if (Objects.equals(stringRedisTemplate.opsForValue().get(id), stockName)){
            map.put("result", "failure");
            return map;
        }
        mqStockService.secKill(id, stockName, stockId);
        map.put("result", "success");
        return map;
    }

    /**
     * 通过前端传参进行获取用户名呈现在商品列表上
     * @param model
     * @param accountId
     * @return
     */
    @RequestMapping("/good_list")
    public String goodList (Model model, @RequestParam("username")String accountId){
        List<Stock> stockList = stockService.selectAll();
        System.out.println(accountId);
        //将属性名放入对应的属性当中打包到model中一并返回给html文件
        model.addAttribute("stockList", stockList);
        model.addAttribute("username", accountId);
        //返回相应的html地址
        return "Stock_List";//viewname
    }

    /**
     * 通过前端动态传输REST风格的参数，@PathVariable指定接受rest风格的参数，@RequestParam将会传承无效
     * @param model
     * @param goodId
     * @return
     */
    @RequestMapping("/good_detail/{id}/{username}")
    public String goodDetail(Model model, @PathVariable("id")int goodId, @PathVariable("username")String username){
        Seckillgoods stock = stockService.selectById(goodId);
        //获取对应动态商品id的信息存储在model内返回给前端页面渲染
        model.addAttribute("stock", stock);
//        model.addAttribute("username", username);
//        System.out.println(username);

        return "Stock_Detail";
    }

    @RequestMapping("/payOrder")
    @ResponseBody
    public Map<String, Object> payOrder(@RequestParam("stockId")int id, @RequestParam("orderUser") String username){
        //设置缓存状态交由消息接收者判断是否支付进行数据库更改和回退
        //无过期时间
        stringRedisTemplate.opsForValue().set(username, "已支付");
        Map<String, Object> map = new HashMap<>();
        map.put("result", "success");
        return map;
        //获取对应动态商品id的信息存储在model内返回给前端页面渲染
//        model.addAttribute("username", username);
//        System.out.println(username);
    }

    @RequestMapping("/order_detail/{username}/{id}")
    public String OrderDetail(Model model, @PathVariable("username")String username, @PathVariable("id")int id){
        Seckillgoods stock = stockService.selectById(id);
        //获取对应动态商品id的信息存储在model内返回给前端页面渲染
        model.addAttribute("username", username);
        model.addAttribute("id", id);
        model.addAttribute("stock", stock);
//        model.addAttribute("username", username);
//        System.out.println(username);

        return "Order_Detail";
    }

}
