package com.example.my_project.controller;

import com.example.my_project.entity.User;
import com.example.my_project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**用户信息-Controller
 * 和页面进行数据交互
 * 调用和Service获取数据
 */
@Controller
public class IUserController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping( value = "/" )
    public String loginPage() {
        return "/login&registry";
    }

    @RequestMapping("/check")
    @ResponseBody
    public Map<String, Object> checkExsitence (@RequestParam("accountId") String accountId){
        List<User> users = iUserService.findById(accountId);
        Map<String, Object> map = new HashMap<String, Object>();
        String result = null;
        User user = new User();
        for (int i = 0; i < users.size(); i++){
            user = users.get(i);
            if (Objects.equals(user.getAccountId(), accountId)) {
                result = "已注册";
                map.put("msg", result);
                map.put("code", "1");
                System.out.println("已注册");
                return map;
            }
        }
        result = "未注册";
        map.put("msg", result);
        map.put("code", "0");
        System.out.println("未注册");
        return map;
    }

    @RequestMapping( "/findAll" )
    public List<User> findAllUsers() {
        List<User> users = iUserService.findAll();
        return users;
    }

    @RequestMapping( "/updatePassword" )
    @ResponseBody
    public Map<String, Object> updatePassword(@RequestParam("accountId") String accountId, @RequestParam("password") String password, @RequestParam("newpassword") String newpassword,
                                              @RequestParam("newpassword2") String newpassword2) {
        List<User> user = iUserService.login(accountId, password);
        Map<String, Object> map = new HashMap<String, Object>();
        String result = null;
        User users = new User();
        for (int i = 0; i < user.size(); i++) {
            users = user.get(i);
            if ((Objects.equals(users.getAccountId(), accountId)) && (Objects.equals(users.getPassword(), password))) {
                if (Objects.equals(newpassword, newpassword2)) {
                    if (newpassword == "" && newpassword2 == ""){
                        result = "新密码不能为空。请重新输入";
                        map.put("msg", result);
                        map.put("code", '0');
                        System.out.println("修改失败");
                        return map;
                    }
                    else {
                        iUserService.updatePassword(accountId, newpassword);
                        result = "修改成功，跳转回登录页面";
                        map.put("msg", result);
                        map.put("code", "1");
                        System.out.println("修改成功");
                        return map;
                    }
                }
                else {
                    map.put("code", "2");
                    System.out.println("密码不匹配，请重新输入");
                    return map;
                }
            }
        }
        result = "账号密码错误， 请重新输入";
        map.put("msg", result);
        map.put("code", '0');
        System.out.println("修改失败");
        return map;
    }

    @RequestMapping( "/registry" )
    public String registry(String accountId, String password) {
        iUserService.registration(accountId, password);
        return "success&registration";
    }

    @RequestMapping( "/login" )
    @ResponseBody
    //前端异步发送到这个地址进行账户密码校验，返回数据给前端进行判断
    public Map<String, Object> login(String accountId, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        String result = null;
        List<User> user = iUserService.login(accountId, password);
        //创建User对象，用于储存各个List<User>对象
        User users = new User();
        //遍历所有List<User>
        for (int i = 0; i < user.size(); i++) {
            users = user.get(i);
            if ((Objects.equals(users.getAccountId(), accountId)) && (Objects.equals(users.getPassword(), password))) {
                result = "登陆成功";
                map.put("msg", result);
                map.put("code", "1");
                System.out.println("登录成功");
                return map;
            }
        }
        //返回一个map包含报错信息用于前端界面弹窗
        result = "账户密码错误";
        map.put("msg", result);
        map.put("code", "0");
        System.out.println("登录失败");
        return map;
    }
}
