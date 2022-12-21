package com.example.my_project.service;

import com.example.my_project.entity.User;

import java.util.List;
//用户账号信息-Service
//业务层接口
//调用mapper(aka dao)查询方法
public interface IUserService {
    /**查询所有用户信息
     *
     * @return user_information.userIformation
     */
    List<User> findAll();

    /**通过特定id查询特定用户
     *
     */
    List<User> findById(String accountId);

    void registration(String accountId, String password);

     void updatePassword(String accountId, String password);

    List<User> login(String accountId, String password);
}
