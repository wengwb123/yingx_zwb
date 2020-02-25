package com.baizhi.zwb.action;

import com.alibaba.fastjson.JSON;
import com.baizhi.zwb.entity.User;
import com.baizhi.zwb.service.UserService;
import com.baizhi.zwb.service.UserServiceImpl;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    //实现分页查询所有用户
    @ResponseBody
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        final List<User> users = userService.queryAll(page, rows);
        int count = userService.queryAllCount();
        int total = (count % rows == 0) ? (count / rows) : (count / rows + 1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("records",count);
        map.put("total",total);
        map.put("rows",users);
        return map;
    }

    //实现用户的增删改编辑操作
    @ResponseBody
    @RequestMapping("/edit")
    public String edit(User user,String oper){
        String id = null;
        if(oper.equals("edit")){
            System.out.println("修改操作");
        }else if(oper.equals("add")){
            id = userService.add(user);

            //动态显示用户注册
            //将其转换称json类型
            System.out.println("开始");
            Map<String, Object> map = userService.makeGraph();
            String jsonString = JSON.toJSONString(map);
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-081bbf854e0e4dc4bcbf8ad1fa773068");
            goEasy.publish("我的channel", jsonString);
            System.out.println("结束");

        }else if(oper.equals("del")){
            System.out.println("删除操作");
        }
        return id;
    }

    //实现更该用户信息的方法
    @ResponseBody
    @RequestMapping("/editStatus")
    public void editStatus(User user){
        userService.editStatus(user);
    }

    //用户的头像上传
    @ResponseBody
    @RequestMapping("/uploadFile")
    public void uploadFile(MultipartFile picImg, String id, HttpSession session) throws IOException {
        String path = session.getServletContext().getRealPath("/upload/img");
        File file = new File(path);
        if(!file.exists()) file.mkdirs();
        String fileName = new Date().getTime()+picImg.getOriginalFilename();
        picImg.transferTo(new File(path,fileName));
        User user = new User();
        user.setId(id);
        user.setPicImg(fileName);
        userService.editPicImg(user);
    }

    //给用户发送短信验证
    @ResponseBody
    @RequestMapping("sendMessage")
    public void sendMessage(String phone){
        userService.sendMessage(phone);
    }

    //导出用户信息到excel表格
    @ResponseBody
    @RequestMapping("exportMessage")
    public void exportMessage(){
        userService.exportMessage();
    }

    //对于用户注册按月份进行统计分布
    @ResponseBody
    @RequestMapping("makeGraph")
    public Map<String, Object> makeGraph(){
        Map<String, Object> map = userService.makeGraph();
        return map;

    }
}
