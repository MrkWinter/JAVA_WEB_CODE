package com.itheima.config;

import com.itheima.service.DeptService;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MrkWinter
 * @version 1.0
 */
//配置类
@Configuration
public class CommonConfig {
    @Bean
    //将当前方法的返回值对象交给IOC容器 成为IOC容器bean
    //1. 通过过@Bean交给IOC容器的对象名默认为方法名
    //2. 可以通过@Bean注解的name或value属性声明bean的名称
    //3. 第三方bean需要依赖其他bean对象可以直接在bean定义方法中设置形参即可 容器会根据类型自动装配
    public SAXReader sexReader(DeptService deptService) {
        System.out.println(deptService);
        return new SAXReader();
    }
}
