package com.example.demo.config;

import com.example.demo.annotation.ThreadPoolStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Aspect
@Component
public class TheadPoolStatusAdvice {

    @Resource(name = "demoAsyncServer")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Pointcut("@annotation(com.example.demo.annotation.ThreadPoolStatus)")
    public void threadPoolStatus(){}


    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };

//    原文链接：https://blog.csdn.net/bicheng4769/article/details/80007362

    @Around("threadPoolStatus()")
    public Object threadPoolDoing(ProceedingJoinPoint point) throws Throwable {
        Object target = point.getTarget();
        Class<?> aClass = target.getClass();
        String name = aClass.getName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                //获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
                Class s = map.get(result);
                classes[k] = s == null ? args[k].getClass() : s;
            }
        }
        //获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Method method = Class.forName(name).getMethod(methodName, classes);
        ThreadPoolStatus annotation = method.getAnnotation(ThreadPoolStatus.class);
        showThreadPoolInfo(annotation.value());
        return point.proceed();
    }


    private void showThreadPoolInfo(String prefix){
        ThreadPoolExecutor threadPoolExecutor = threadPoolTaskExecutor.getThreadPoolExecutor();
        if(null==threadPoolExecutor){
            return;
        }
        log.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                threadPoolTaskExecutor.getThreadNamePrefix(),
                prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }

//    原文链接：https://blog.csdn.net/boling_cavalry/article/details/79120268
}
