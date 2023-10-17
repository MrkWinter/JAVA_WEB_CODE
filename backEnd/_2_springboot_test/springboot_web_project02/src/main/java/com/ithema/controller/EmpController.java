package com.ithema.controller;

import com.ithema.pojo.Emp;
import com.ithema.service.EmpService;
import com.ithema.pojo.Result;
import com.ithema.service.impl.EmpServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    @RequestMapping("/listEmp")
    public Result list() {

        //面向接口编程  类中定义
        List<Emp> empList = empService.listEmp();

        return Result.success(empList);

//        //1. 加载并解析xml文件
//        //基于classLouder或取资源路径 应该是获取类加载器 这个项目下的资源进行检索找到 返回文件路径
//        String file = this.getClass().getClassLoader().getResource("emp.xml").getFile();
//        List<Emp> empList = XmlParserUtils.parse(file, Emp.class);
//        //2. 对数据进行转换处理
//        empList.stream().forEach(emp -> {
//            String gender = emp.getGender();
//            if ("1".equals(gender)) {
//                emp.setGender("男");
//            } else {
//                emp.setGender("女");
//            }
//            String job = emp.getJob();
//            if ("1".equals(job)) {
//                emp.setJob("讲师");
//            } else if ("2".equals(job)) {
//                emp.setJob("班主任");
//            } else {
//                emp.setJob("就业指导");
//            }
//        });
//        //3. 响应数据
//        return Result.success(empList);
    }
}
