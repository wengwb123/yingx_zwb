package com.baizhi.zwb.action;

import com.baizhi.zwb.entity.Admin;
import com.baizhi.zwb.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
    public String login(Admin admin, String enCode){
        //adminService.adminLogin(admin,enCode);

        //通过shiro来进行认证
        System.out.println("用户名："+admin.getUsername()+"  密码："+admin.getPassword());

        //根据安全工具类获取主体对象
        Subject subject = SecurityUtils.getSubject();

        //将用户名和密码放入token
        AuthenticationToken token=new UsernamePasswordToken(admin.getUsername(),admin.getPassword());

        //认证
        try {
            subject.login(token);
            boolean authenticated = subject.isAuthenticated();

            System.out.println("认证成功，状态："+authenticated);

            return "redirect:/main/main.jsp";
        } catch (UnknownAccountException e) {
            System.out.println("未知的账号异常====用户不存在");
            return "redirect:/login/login1.jsp";
        }catch (IncorrectCredentialsException e) {
            System.out.println("不正确的凭证异常====密码不正确");
            return "redirect:/login/login1.jsp";
        }
    }

    //实现退出功能
    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}
