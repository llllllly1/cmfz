package com.baizhi.ly.ascept;

import com.baizhi.ly.entity.Admin;
import com.baizhi.ly.entity.Log;
import com.baizhi.ly.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Component
@Aspect
public class AroundAdvice {
    @Autowired
    HttpServletRequest request;


    @Autowired
    LogService logService;

    @Around(value = "pt()")
    public Object LoginAround(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("this is around before");
        //怎么获取执行操作的用户
        Admin admin = (Admin) request.getSession().getAttribute("admin");

        //怎么获取做了什么事
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解对象
        Logs logs = method.getAnnotation(Logs.class);
        String name = logs.name();

        Object proceed=null;
        //状态为1 成功  为0 失败
        Integer status = 0;

        try {
            proceed = proceedingJoinPoint.proceed();
            status = 1;
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            Log log = new Log(UUID.randomUUID().toString(),admin.getUsername(),name,status,new Date());
            logService.insertLog(log);
        }
        return proceed;
    }


    @Pointcut(value = "@annotation(com.baizhi.ly.ascept.Logs)")
    public void pt(){

    }
}
