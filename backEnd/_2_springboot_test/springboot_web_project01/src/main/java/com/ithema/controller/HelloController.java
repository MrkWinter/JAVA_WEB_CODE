package com.ithema.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MrkWinter
 * @version 1.0
 */
//请求处理类
@RestController
public class HelloController {
    //指定当前方法处理的是那个请求
    @RequestMapping("/hello")
    public String hello() {
        System.out.println("Hello");
        return "Hello World";
    }
}
