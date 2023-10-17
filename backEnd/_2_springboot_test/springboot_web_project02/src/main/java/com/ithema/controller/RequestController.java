package com.ithema.controller;

import com.ithema.pojo.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
//请求控制类
    @RestController
public class RequestController {
    //简单参数
//    @RequestMapping("/simpleParam")
//    public String simpleParam(HttpServletRequest request) {
//        String name = request.getParameter("name");
//        String ageStr = request.getParameter("age");
//        int age = Integer.parseInt(ageStr);
//        System.out.println(name +":" + age);
//        return "OK";
//    }
//SpringBoot方式接收
//        @RequestMapping("/simpleParam")
//    public String simpleParam(String name,Integer age) {
//        System.out.println(name +":" + age);
//        return "OK";
//    }

//    //SpringBoot方式接收
//    @RequestMapping("/simpleParam")
//    public String simpleParam(@RequestParam(name="name",required = false) String nameStr, Integer age) {
//        System.out.println(nameStr +":" + age);
//        return "OK";
//    }

//    //实体参数接收
//    @RequestMapping("/simpleParam")
//    public String simpleParam(User user) {
//        System.out.println(user.getName() +":" + user.getAge());
//        return "OK";
//    }

    //复杂实体参数接收
    //对于User中的Address对象 浏览器传参时只关注信息 不关注对象 但需要声明是那个对象中的信息
    //如 address.province=河南&address.city=周口
//    @RequestMapping("/simpleParam")
//    public String simpleParam(User user) {
//        System.out.println(user);
//        return "OK";
//    }
//
//@RequestMapping("arrayParam")
//    public String arrayParam(String[] hobby) {
//        System.out.println(Arrays.toString(hobby));
//        return "OK";
//    }


//    @RequestMapping("arrayParam")
//    public String arrayParam(@RequestParam List<String> hobby) {
//        System.out.println(hobby);
//        return "OK";
//    }
//    @RequestMapping("dateParam")
//    public String dateParam(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime localDateTime) {
//        System.out.println(localDateTime);
//        return "OK";
//    }
//@RequestMapping("jsonParam")
////将json格式的数据封装到对象中
//    public String jsonParam(@RequestBody User user) {
//        System.out.println(user);
//        return "OK";
//    }
    //{id} 表示后半部分作为路径参数 是可变的 获取id后@PathVariable绑定的相同名的形参id上
//    @RequestMapping("path/{id}")
//    public String pathParam(@PathVariable Integer id) {
//        System.out.println(id);
//        return "OK";
//    }

    //可以使用/{}/{...}/..来接收传递的多个路径参数 注意每个路径参数都需要通过@PPathVariable注解绑定形参
    @RequestMapping("path/{id}/{name}")
    public String pathParam(@PathVariable Integer id,@PathVariable String name) {
        System.out.println(id +":" + name);
        return "OK";
    }
}
