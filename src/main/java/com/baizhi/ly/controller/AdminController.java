package com.baizhi.ly.controller;

import com.baizhi.ly.entity.Admin;
import com.baizhi.ly.service.AdminService;
import com.baizhi.ly.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //登陆
    @ResponseBody
    @RequestMapping("adminLogin")
    public String loginAdmin(String name,String password,String code,HttpSession session, HttpServletRequest request){
        Admin admin=null;
        admin = adminService.selectOne(name);
        String message="登陆成功";
        if (!code.equals(session.getAttribute("code"))){
            message="验证码错误";
        }else if (admin==null){
            message="用户不存在";
        }else if(!admin.getPassword().equals(password)){
            message="密码错误";
        }
        if (message.equals("登陆成功")){
            session.setAttribute("admin",admin);
        }
        request.setAttribute("message",message);
        return  message;
    }


    //验证码
    @RequestMapping("creatImage")
    public void creatImage(HttpSession session, HttpServletResponse response) throws IOException {
        //创建工具类对象
        CreateValidateCode createValidateCode = new CreateValidateCode();
        //调用工具类，获取随机验证码
        String code = createValidateCode.getCode();

        //将随机验证码存入session
        session.setAttribute("code",code);

        //将随机验证码生成图片
        createValidateCode.write(response.getOutputStream());

        System.out.println("生成了验证码");

    }
}
