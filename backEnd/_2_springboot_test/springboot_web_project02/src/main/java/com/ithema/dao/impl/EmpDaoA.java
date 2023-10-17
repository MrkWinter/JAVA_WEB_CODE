package com.ithema.dao.impl;

import com.ithema.dao.EmpDao;
import com.ithema.pojo.Emp;
import com.ithema.utils.XmlParserUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
@Component
public class EmpDaoA implements EmpDao {
    @Override
    public List<Emp> listEmp() {
        //1. 加载并解析xml文件
        //基于classLouder或取资源路径 应该是获取类加载器 这个项目下的资源进行检索找到 返回文件路径
        String file = this.getClass().getClassLoader().getResource("emp.xml").getFile();
        List<Emp> empList = XmlParserUtils.parse(file, Emp.class);
        return empList;
    }
}
