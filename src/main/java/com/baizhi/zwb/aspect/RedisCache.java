package com.baizhi.zwb.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

/*
*用来制作redis的缓存
* 添加两个方法，一个在查询时添加缓存，另一个在增删改时清空缓存
*/
@Aspect
@Configuration
public class RedisCache {

    /*@Resource
    RedisTemplate redisTemplate;

    //环绕通知
    @Around(value = "@annotation(com.baizhi.zwb.annotation.AddCache)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //解决乱码问题
        *//*序列化解决乱码*//*
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //用来操作redis
        ValueOperations opsForValue = redisTemplate.opsForValue();

        //用来拼接字符串,用来作为键
        StringBuilder key = new StringBuilder();

        //使用redis的String类型来进行存储

        //获取切面类的权限定名
        String name = proceedingJoinPoint.getTarget().getClass().getName();
        key.append(name);

        //获取切面类的方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        key.append(methodName);

        //获取参数  实参
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            key.append(arg);
        }

        //判断如果缓存中存在，则直接从缓存中取值
        Object value = null;
        Boolean aBoolean = redisTemplate.hasKey(key);
        if(aBoolean){
            //从数据库中取值
            value = opsForValue.get(key);

        }else {
            //若不存在，则查询数据库
            value = proceedingJoinPoint.proceed();
            //并且将查询结果存入缓存中
            opsForValue.set(key,value);
        }

        return value;
    }

    //后置通知
    @After(value = "@annotation(com.baizhi.zwb.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){
        //获取类的权限定名
        String name = joinPoint.getTarget().getClass().getName();

        //查询数据库的所有键
        Set<String> keys = redisTemplate.keys("*");

        for (String key : keys) {
            if(key.startsWith(name)){
                redisTemplate.delete(key);
            }
        }
    }*/

}
