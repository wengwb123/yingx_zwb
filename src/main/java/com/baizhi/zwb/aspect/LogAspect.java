package com.baizhi.zwb.aspect;

import com.baizhi.zwb.annotation.AddLog;
import com.baizhi.zwb.dao.LogMapper;
import com.baizhi.zwb.entity.Admin;
import com.baizhi.zwb.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspect {

    @Resource
    HttpServletRequest request;
    @Resource
    LogMapper logMapper;

    //环绕通知
    @Around("@annotation(com.baizhi.zwb.annotation.AddLog)") //注解的全限定名
    public Object addLogs(ProceedingJoinPoint proceedingJoinPoint){

        //获得切到的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取方法信息
        Method method = signature.getMethod();

        //获取方法上的注解   哪个注解
        AddLog addLog = method.getAnnotation(AddLog.class);
        //获取注解中的值    value
        String value = addLog.value();

        //谁   时间   操作   是否成功
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        //时间
        Date date = new Date();
        //操作
        String methodName = proceedingJoinPoint.getSignature().getName();
        try {
            //放行方法
            Object proceed = proceedingJoinPoint.proceed();
            String message="success";
            //日志信息入库
            Log log = new Log(UUID.randomUUID().toString(),"测试",date,value+"("+methodName+")",message);
            logMapper.insertSelective(log);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            return null;
        }
    }

    //环绕通知
    //@Around("execution(* com.baizhi.serviceImpl.*.*(..)) && !execution(* com.baizhi.serviceImpl.*.query*(..)) ")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){

        //谁   时间   操作   是否成功
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        //时间
        Date date = new Date();
        //操作
        String methodName = proceedingJoinPoint.getSignature().getName();
        try {
            //放行方法
            Object proceed = proceedingJoinPoint.proceed();
            String message="success";
            System.out.println(admin.getUsername()+"--"+date+"--"+methodName+"--"+message);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            return null;
        }
    }
}

















