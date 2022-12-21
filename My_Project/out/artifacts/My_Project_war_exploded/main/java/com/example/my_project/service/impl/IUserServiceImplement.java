package com.example.my_project.service.impl;

import com.example.my_project.dao.IUser;
import com.example.my_project.entity.User;
import com.example.my_project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

//业务层实现类
//IUserService的实现类
//用户信息的实现类
@Service//Spring框架创建对象
public class IUserServiceImplement implements IUserService {
    //Service调用mapper&dao层查询数据库
    @Autowired
    private IUser iUser;

    @Override
    public List<User> findAll() {
        return iUser.findAll();
    }

    @Override
    public List<User> findById(String accountId) {
        return iUser.findById(accountId);
    }

    @Override
    public void updatePassword(String accountId, String password) {
        iUser.updatePassword(accountId, password);
    }

    @Override
    public void registration(String accountId, String password) {
        iUser.registration(accountId, password);
    }

    @Override
    public List<User> login(String accountId, String password) {
        return iUser.login(accountId, password);
    }
}
