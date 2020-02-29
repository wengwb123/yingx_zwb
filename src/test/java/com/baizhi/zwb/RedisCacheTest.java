package com.baizhi.zwb;

import com.baizhi.zwb.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*
* 用来测试redis缓存
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheTest {

    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void test(){
        System.out.println("开始测试redis的缓存");
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<User> str = new ArrayList<>();
        str.add(new User("a","a","a","a","a","a","a","a",new Date(),11.2));
        valueOperations.set("names",str);
        Object names = valueOperations.get("names");
        System.out.println(names);
    }
}
