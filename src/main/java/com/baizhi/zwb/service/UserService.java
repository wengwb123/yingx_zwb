package com.baizhi.zwb.service;

import com.baizhi.zwb.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页查询用户的信息(page:当前页号，rows：每页显示的条数)
    List<User> queryAll(Integer page,Integer rows);

    //显示用户的总数
    int queryAllCount();

    //修改用户的状态
    void editStatus(User user);

    //修改用户存储的图片信息
    void editPicImg(User user);

    //添加用户的操作
    String add(User user);

    //给用户发送短信验证
    void sendMessage(String phone);

    //导出用户信息
    public void exportMessage();

    //统计每个月分用户注册人数
    public Map<String,Object> makeGraph();
}
