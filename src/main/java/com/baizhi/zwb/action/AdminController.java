package com.baizhi.zwb.action;

import com.baizhi.zwb.entity.Admin;
import com.baizhi.zwb.exception.AdminLoginException;
import com.baizhi.zwb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //实现管理员登陆
    @RequestMapping("/login")
    public String login(Admin admin,String enCode){
        adminService.adminLogin(admin,enCode);
        return "main/main";
    }

    //实现退出功能
    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}
