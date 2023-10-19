package com.ithema;

import com.ithema.mapper.EmpMapper;
import com.ithema.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MybatisMysqlCrudProject02ApplicationTests {
    @Autowired
    private EmpMapper empMapper;

//    @Test
//    void contextLoads() {
//        empMapper.delete(17);
//    }

    //    @Test
//    void insert() {
//        Emp emp = new Emp();
//        emp.setUsername("Tom");
//        emp.setName("汤姆");
//        emp.setGender((short)1);
//        emp.setImage("1.jpg");
//        emp.setJob((short)1);
//        emp.setEntrydate(LocalDate.of(2020,2,3));
//        emp.setDeptId(1);
//        emp.setCreateTime(LocalDateTime.now());
//        emp.setUpdateTime(LocalDateTime.now());
//        empMapper.insert(emp);
//        System.out.println(emp.getId());
//    }
//    @Test
//    void update() {
//        Emp emp = new Emp();
//        emp.setId(18);
//        emp.setUsername("Tom1");
//        emp.setName("汤姆1");
//        emp.setImage("1.jpg");
//        emp.setGender((short)1);
//        emp.setJob((short)1);
//        emp.setEntrydate(LocalDate.of(2000,1,1));
//        emp.setUpdateTime(LocalDateTime.now());
//        emp.setDeptId(1);
//
//        empMapper.update(emp);
//    }
//    @Test
//    public void select() {
//        Emp emp = empMapper.select(18);
//        System.out.println(emp);
//    }
//    @Test
//    public void list() {
//        List<Emp> emp = empMapper.list("张", (short) 1, LocalDate.of(2010, 1, 1), LocalDate.of(2020, 1, 1));
//        System.out.println(emp);
//    }

    //动态sql
//    @Test
//    public void list() {
//        List<Emp> emp = empMapper.list("张", (short) 1, null, null);
//        System.out.println(emp);
//    }

    @Test
    public void deleteByIds() {
        empMapper.deleteByIds(Arrays.asList(1,2,3));
    }

}
