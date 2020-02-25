package com.baizhi.zwb.service;

import com.baizhi.zwb.annotation.AddLog;
import com.baizhi.zwb.dao.AdminDao;
import com.baizhi.zwb.entity.Admin;
import com.baizhi.zwb.exception.AdminLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminDao adminDao;

    @Autowired
    HttpSession session;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @AddLog(value = "管理员登陆")
    public void adminLogin(Admin admin,String enCode) {
        String code = (String) session.getAttribute("code");
        if(!code.equals(enCode)) throw new AdminLoginException("验证码错误");
        Admin admin1 = adminDao.selectByUsername(admin.getUsername());
        if(admin1 == null) throw new AdminLoginException("账号错误");
        if(!admin1.getPassword().equals(admin.getPassword())) throw new AdminLoginException("密码错误");
        session.setAttribute("admin",admin);
    }
}
