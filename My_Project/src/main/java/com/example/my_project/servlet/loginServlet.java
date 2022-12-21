package com.example.my_project.servlet;

import com.example.my_project.controller.IUserController;
import com.example.my_project.dao.IUser;
import com.example.my_project.entity.User;
import com.example.my_project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = "/login")
public class loginServlet extends HttpServlet {
    @Autowired
    private IUserService iUserService;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{
        doPost(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //测试是否获取成功
        //System.out.println("request.getClass() = " + request.getClass());
        String uName = request.getParameter("accountId");
        String uPassword = request.getParameter("password");
        //测试是否获得入参
//        System.out.println(uName);
//        System.out.println(uPassword);
        List<User> user = iUserService.login(uName, uPassword);
        User users = new User();
        //遍历所有List<User>d
        for (int i = 0; i < user.size(); i++) {
            users = user.get(i);
            if ((Objects.equals(users.getAccountId(), uName)) && (Objects.equals(users.getPassword(), uPassword))){
                response.sendRedirect("templates/error.jsp");
                return;
            }
        }
        request.getRequestDispatcher("templates/error.jsp").forward(request, response);    }
}
