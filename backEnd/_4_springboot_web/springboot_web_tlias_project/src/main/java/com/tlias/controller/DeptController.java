package com.tlias.controller;

import com.tlias.pojo.Dept;
import com.tlias.pojo.Result;
import com.tlias.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
//4. lombok中内置了该注解用于自动生成Logger日志对象 对象名为log
@Slf4j
//5. @RestController注解中包含 @Controller @ResponseBody
// @controller表示将该类交给Service服务器(DispactcherServlet类 前端控制器 其控制注解@Controller的类)
// 通过接管该类及其中的响应方法将项目部署到服务器上
// @ResponseBody表示该类中的所有方法的返回值都作为json对象/数组 返回给前端浏览器
@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    //3.该Logger对象用与记录日志文件
    // private static Logger log = (Logger) LoggerFactory.getLogger(DeptController.class);
    //1. 用RequestMapping中的value属性指定访问路径 method属性指定访问方法
    // @RequestMapping(value = "/depts",method = RequestMethod.GET)
    //2. 可以用RequestMapping的衍生注解简化
    @GetMapping
    public Result list() {
        log.info("查询全部部门数据");
        //6. 调用service对象list
        List<Dept> result = deptService.list();
        return Result.success(result);
    }


    //删除部门操作
    //1.  @DeleteMapping 指定访问路径(删除特有的请求方式)
    //2.  @PathVariable用于路径参数的绑定
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除部门{}", id);
        //info方法中的{}为参数占位符
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 新增部门操作
     *
     * @param dept
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    //根据id查询数据 用于页面回显
    //前端怎么做到的
    //得到数据展示时有隐藏的数据模型 根据编辑时自动得到对应的id??? 显示数据
    // 根据id在进行修改数据?? 此步多此一举????
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        log.info("根据id查询部门{}",id);
        Dept dept = deptService.selectById(id);
        return Result.success(dept);
    }


    /**
     * 根据id修改
     *
     * @param dept
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门{}", dept.getId());
        deptService.update(dept);
        return Result.success();
    }
}
