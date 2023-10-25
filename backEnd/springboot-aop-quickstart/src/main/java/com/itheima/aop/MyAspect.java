package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.Jar;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author MrkWinter
 * @version 1.0
 */
@Slf4j
@Component
@Aspect
public class MyAspect {
    @Before("execution(* com.itheima.service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        System.out.println("before");
    }

    @Around("execution(* com.itheima.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //1. 获取目标对象的类名
        String name = joinPoint.getTarget().getClass().getName();
        log.info("目标对象类名{}", name);
        //2. 获取目标方法的方法名
        String name1 = joinPoint.getSignature().getName();
        log.info("获取目标方法的方法名{}", name1);
        //3. 获取目标方法运行时传入参数
        Object[] args = joinPoint.getArgs();
        log.info("获取目标方法运行时传入参数{}", Arrays.toString(args));

        //4. 放行目标方法 后执行 获取返回值
        Object result = joinPoint.proceed();
        log.info("执行后获取返回值{}", result);

        return result;
    }
}
