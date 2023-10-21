package com.tlias.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tlias.mapper.EmpMapper;
import com.tlias.pojo.Emp;
import com.tlias.pojo.PageBean;
import com.tlias.service.EmpService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;


//    @Override
//    public PageBean query(String name, String gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
//        if (pageSize == null)
//            pageSize = 10; //默认为10
//        //根据条件返回记录数
//        Long total = empMapper.getTotal(name, gender, begin, end);
//        //根据页码返回数据列表
//        List<Emp> rows = empMapper.getRows((page-1)*pageSize, pageSize);
//        return new PageBean(total, rows);
//    }

    //使用pagehelper简化分页查询操作
    public PageBean query(String name, Short gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
        //设置页数 以及分页数
        PageHelper.startPage(page,pageSize);
        //设置页数 以及分页数后会自动执行 select count(*) from emp 然后自动赋值给该类的属性 total
        List<Emp> list = empMapper.getList(name, gender, begin, end);
//        @Select("select * from emp")
//        List<Emp> getList();
        //执行该list方法后会自动在SQL语句后加上 limit start,row
        //返回得到的元素列表 (该列表编译类型为集合 运行类型为Page类型) 可以强转为Page类型
        Page<Emp> p = (Page<Emp>) list;
        //使用该类的方法得到元素个数 和 元素集合
        return new PageBean(p.getTotal(), p.getResult());
    }

    //用于批量删除
    @Override
    public void deleteById(List<Integer> ids) {
        empMapper.deleteById(ids);
    }

    @Override
    public void save(Emp emp) {
        //补充数据
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }
}
