package com.tlias.controller;

import com.tlias.pojo.Emp;
import com.tlias.pojo.PageBean;
import com.tlias.pojo.Result;
import com.tlias.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;
    /**
     * 用于分页查询
     * @param name
     * @param gender
     * @param begin
     * @param end
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    //可以使用@RequestParam给参数设置默认值
    public Result query(String name, Short gender, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询 返回数据");
        PageBean pageBean = empService.query(name,gender,begin,end,page,pageSize);
        return Result.success(pageBean);
    }

    /**
     * 用于批量删除
     * @param ids
     * @return
     */
    //路径参数用 @PathVariable 绑定 数组用集合接收 前端传递数据为/1,2,3
    //get请求下 请求头后以?ids=1&ids=2的方式也可以传递给集合或数组 @RequestParam用于传递给集合 普通数组不用注解
    @DeleteMapping("/{ids}")
    public Result deleteById(@PathVariable List<Integer> ids) {
        log.info("批量删除{}",ids);
        empService.deleteById(ids);
        return Result.success();
    }

    /**
     * 用于新增员工
     * @param emp
     * @return
     */
    @PostMapping
    public Result sava(@RequestBody Emp emp) {
        log.info("新增员工{}",emp);
        empService.save(emp);
        return Result.success();
    }
}
