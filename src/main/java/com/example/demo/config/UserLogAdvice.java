package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class UserLogAdvice {

    @Pointcut("@annotation(com.example.demo.annotation.UserLog)")
    public void userLog(){}

    /**
     * 前置通知，方法调用前被调用 
     * @param joinPoint/null
     */
    @Before(value = "userLog()")
    public void before(JoinPoint joinPoint){
        log.info("前置通知");
        //获取目标方法的参数信息  
        Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息  
        joinPoint.getThis();
        //代理的目标对象  
        joinPoint.getTarget();
        //用的最多 通知的签名  
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法  
        log.info("代理的是哪一个方法"+signature.getName());
        //AOP代理类的名字  
        log.info("AOP代理类的名字"+signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息  
        signature.getDeclaringType();
        //获取RequestAttributes  
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息  
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //如果要获取Session信息的话，可以这样写：  
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);  
        //获取请求参数
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String,String> parameterMap = new HashMap<>();
        while (enumeration.hasMoreElements()){
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter,request.getParameter(parameter));
        }
        if(obj.length > 0) {
            log.info("请求的参数信息为："+parameterMap.toString());
        }
    }

    @Around("userLog()")
    public Object userDoing(ProceedingJoinPoint point) throws Throwable {
        return point.proceed();
    }
}
