package com.baizhi.zwb;

import com.baizhi.zwb.dao.UserMapper;
import com.baizhi.zwb.entity.Log;
import com.baizhi.zwb.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {

    @Resource
    UserMapper userMapper;

    @Test
    public void contextLoads() {
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
    
    @Test
    public void test1(){
        Log log = new Log(UUID.randomUUID().toString(),"111",new Date(),"aaa","1");
        System.out.println(log);
    }

}
