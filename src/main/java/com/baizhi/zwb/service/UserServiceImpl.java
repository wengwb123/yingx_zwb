package com.baizhi.zwb.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.zwb.annotation.AddCache;
import com.baizhi.zwb.annotation.AddLog;
import com.baizhi.zwb.annotation.DelCache;
import com.baizhi.zwb.dao.UserMapper;
import com.baizhi.zwb.entity.Graph;
import com.baizhi.zwb.entity.Student;
import com.baizhi.zwb.entity.User;
import com.baizhi.zwb.entity.UserExample;
import com.baizhi.zwb.util.AliyunUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Autowired
    HttpSession session;

    @Override
    @AddCache
    public List<User> queryAll(Integer page, Integer rows) {
        UserExample example = new UserExample();
        RowBounds bounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userMapper.selectByExampleAndRowBounds(example, bounds);
        return users;
    }

    @Override
    @AddCache
    public int queryAllCount() {
        int i = userMapper.selectCount(new User());
        return i;
    }

    @Override
    @AddLog(value = "更改用户状态")
    @DelCache
    public void editStatus(User user) {
        if("1".equals(user.getStatus())){
            user.setStatus("0");
        }else {
            user.setStatus("1");
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @AddLog(value = "跟新用户头像")
    @DelCache
    public void editPicImg(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @AddLog(value = "添加用户")
    @DelCache
    public String add(User user) {
        String uId = UUID.randomUUID().toString();
        user.setId(uId);
        user.setStatus("1");
        user.setCreateDate(new Date());
        user.setScore(0.0);
        userMapper.insertSelective(user);
        return uId;
    }

    @Override
    public void sendMessage(String phone) {
        AliyunUtil.sendPhone(phone);
    }

    @Override
    @DelCache
    public void exportMessage(){
        List<User> users = userMapper.selectAll();

        //补全它的路径来导出图片
        String realPath = session.getServletContext().getRealPath("/upload/img");
        for (User user : users) {
            user.setPicImg(realPath+"/"+user.getPicImg());
        }
        System.out.println("开始");
        //配置导出参数  参数：标题,工作表名称
        ExportParams exportParams = new ExportParams("用户信息表", "用户信息");
        //使用导出工具类中方法做导出  参数：导出参数对象,实体类对象,要导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);
        //导出Excel到本地
        try {
            workbook.write(new FileOutputStream(new File("e:\\用户.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }

    @Override
    @AddCache
    public Map<String, Object> makeGraph() {
        HashMap<String, Object> map = new HashMap<>();
        //查询所有月份男女生注册的人数
        List<Graph> graphs = userMapper.selectAllGroupByMothAndSex();
        //设置月份集合
        List<String> months = Arrays.asList("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月");
        //设置男生的注册人数
        List<Integer> males =  Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
        //设置女生的注册人数
        List<Integer> females =  Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
        for (Graph graph : graphs) {
            if(graph.getSex().equals("m")){
                //男设置
                males.set(Integer.parseInt(graph.getMoth())-1,graph.getNum());
            }else{
                //女设置
                females.set(Integer.parseInt(graph.getMoth())-1,graph.getNum());
            }
        }
        //放到map中返回
        map.put("month",months);
        map.put("male",males);
        map.put("female",females);
        return map;
    }
}
