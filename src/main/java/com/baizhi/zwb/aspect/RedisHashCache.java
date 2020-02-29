package com.baizhi.zwb.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

/*
*用来制作redis的缓存:使用来缓存中的hash数据类型
* 添加两个方法，一个在查询时添加缓存，另一个在增删改时清空缓存
*/
@Aspect
@Configuration
public class RedisHashCache {

    @Resource
    RedisTemplate redisTemplate;

    //环绕通知
    @Around(value = "@annotation(com.baizhi.zwb.annotation.AddCache)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //解决乱码问题
        //序列化解决乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);


        //使用redis的String类型来进行存储

        //获取切面类的权限定名,作为外面的key
        String key = proceedingJoinPoint.getTarget().getClass().getName();

        //用来拼接字符串,用来作为value里面的key
        StringBuilder sb = new StringBuilder();

        //获取切面类的方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);

        //获取参数  实参
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }

        String vaKey = sb.toString();

        //用来操作redis
        HashOperations hashOperations = redisTemplate.opsForHash();

        //判断如果缓存中存在，则直接从缓存中取值
        Object result = null;
        Boolean aBoolean = hashOperations.hasKey(key, vaKey);

        if(aBoolean){
            //从数据库中取值
            result = hashOperations.get(key, vaKey);

        }else {
            //若不存在，则查询数据库
            result = proceedingJoinPoint.proceed();
            //并且将查询结果存入缓存中
            hashOperations.put(key,vaKey,result);
        }

        return result;
    }

    //后置通知
    @After(value = "@annotation(com.baizhi.zwb.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){
        //获取类的权限定名
        String name = joinPoint.getTarget().getClass().getName();

        //删除该键对于的值
        redisTemplate.delete(name);
    }

}
