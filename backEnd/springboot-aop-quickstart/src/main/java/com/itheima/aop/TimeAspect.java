package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author MrkWinter
 * @version 1.0
 */
@Slf4j
//@Aspect  //标注为AOP类
@Component  //交给IOC容器
public class TimeAspect {
    @Around("execution(* com.itheima.service.*.*(..))") //切入点表达式 指定那些方法运行以下公共的逻辑代码
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //1. 记录开始时间
        long begin = System.currentTimeMillis();
        //2. 调用原始方法运行
        Object result = joinPoint.proceed();
        //3. 记录结束时间 计算执行耗时

        long end = System.currentTimeMillis();
        log.info(joinPoint.getSignature() + "方法执行耗时{} ms",end - begin);
        return result;
    }
}
