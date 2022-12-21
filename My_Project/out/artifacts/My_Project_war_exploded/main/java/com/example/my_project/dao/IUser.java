package com.example.my_project.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.my_project.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
//User的持久层接口
//和数据库进行交互
public interface IUser {
    //建立实体类和数据库表映射
    @Select("select * from userInformation")
    List<User> findAll();

    //通过id查找用户信息
    //后面业务层实现类进行给予入参userId
    @Select("select * from userInformation where account_Id=#{accountId}")
    List<User> findById(String accountId);

    //实现新用户的注册
    @Insert("insert into userInformation (account_Id, password) values (#{accountId}, #{password})")
    void registration(String accountId, String password);

    //实现用户的登录功能
    @Select("select account_Id, password from userInformation")
    List<User> login(String accountId, String password);

    //实现用户的密码的重置
    @Update("update userInformation set password=#{password} where account_Id=#{accountId}")
    void updatePassword(String accountId, String password);
}
