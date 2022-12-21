package com.example.my_project.service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.my_project.dao.OrderMapper;
import com.example.my_project.dao.StockMapper;
import com.example.my_project.entity.Order;
import com.example.my_project.entity.Stock;
import com.example.my_project.entity.User;
import com.example.my_project.redis.redisConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;

//业务层测试
@RunWith(SpringJUnit4ClassRunner.class)//Spring整合junit
@SpringBootTest
public class Test {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private IStockService iStockService;

//    @Autowired
//    private StringRedisTemplate

    //单元测试
    @org.junit.Test
    public void testFindAll() {
        List<User> users = iUserService.findAll();
        System.out.println(users);
    }

    @org.junit.Test
    public void testFindById() {
        List<User> user = iUserService.findById
                ("123456");
        System.out.println(user);
    }

    @org.junit.Test
    public void testUpdatePassword() {
        List<User> user = iUserService.findById("123456");
        System.out.println(user);//修改密码前的信息
        iUserService.updatePassword("123456", "Gy1098879764");
        user = iUserService.findById("123456");
        System.out.println(user);//修改密码后的信息
    }

    @org.junit.Test
    public void testRegistration() {
        //新创建一个id
        //使用mysql语句调用数据库，新增账号密码
        iUserService.registration("123456", "1008611");
        //用Select语句完成数据表的查找
        List<User> user = iUserService.findAll();
        System.out.println(user);
    }
//    @org.junit.Test
//    public void testRedis(){
//        redisConfig.setCache("如海的夜空", "深邃几万里");
//        System.out.println(redisConfig.getCache("如海的夜空"));
//    }

    @org.junit.Test
    public void insert() {
//        Order orders = new Order();
        Stock stock = new Stock();
//        orders.setId(1);
//        orders.setOrderName("RTX3090");
//        orders.setOrderUser("龚越");
        stock.setStock("1000");
        stock.setName("RTX3070");
        iStockService.saveStock(stock);
//        int num = stockMapper.insert(stock);

//        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();//a
//        orderUpdateWrapper.eq("id", 1).set("order_name", "123").set("order_user", "1234");
//        int num = orderMapper.updateById(orders);

        //使用lambda表达式特定的wrapper
        //继承自AbstractWrapper ,自身的内部属性 entity 也用于生成 where 条件
        //及 LambdaUpdateWrapper, 可以通过 new UpdateWrapper().lambda() 方法获取!
//        LambdaUpdateWrapper<Order> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        orderLambdaUpdateWrapper.eq(Order::getId, 1).set(Order::getOrderName, "健身").set(Order::getOrderUser, "龚越帅哥");
//        int num = orderMapper.update(orders, orderLambdaUpdateWrapper);
//        orders = orderMapper.selectById(1);
//        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
//        orderQueryWrapper.eq("id", 2);
//        int num = orderMapper.update(orders, orderQueryWrapper); //entity和wrapper用于定义生成sql的where条件，update包含两个入参(entity;wrapper)
//        LambdaUpdateWrapper<Order> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        lambdaUpdateWrapper.eq(Order::getId, 2).set(Order::getOrderName, "ETH").set(Order::getOrderUser, "龚越");
//        int num = orderMapper.update(orders, lambdaUpdateWrapper);
//        orders = orderMapper.selectById(2);
        System.out.println(stock);//返回update条数
    }

    @org.junit.Test
    public void AutoFill(){
//        Order order = new Order();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 2).eq("del_flag", 1);
        Order orders = orderMapper.selectOne(queryWrapper);
        System.out.println(orders);
    }

    @org.junit.Test
    public void deleteFill(){
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        stockMapper.delete(queryWrapper);
    }

    @org.junit.Test
    public void recoveryFill(){
        Order order = new Order();
        Stock stock = new Stock();
//        stockMapper.recoveryById(1);
        stock = stockMapper.selectById(1);
        System.out.println(stock);
    }

    @org.junit.Test
    public void selectAll(){
        List<Stock> stockList = iStockService.selectAll();
        for (Stock stock: stockList){
            System.out.println(stock);
        }
    }

    @org.junit.Test
    public void decrFill(){
        iStockService.decrByStock("RTX3090");
    }
}
