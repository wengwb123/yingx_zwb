package com.baizhi.zwb.service;

import com.baizhi.zwb.entity.Admin;

public interface AdminService {
    //实现管理员登陆功能
    void adminLogin(Admin admin,String enCode);
}
