package com.tlias.mapper;

import com.tlias.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
@Mapper
public interface EmpMapper {
//    @Select("select count(id) from emp")
//    Long getTotal(String name, String gender, LocalDate begin, LocalDate end);
//    @Select("select * from emp limit #{start},#{pageSize}")
//    List<Emp> getRows(Integer start, Integer pageSize);

    //使用page简化以上操作helper
    //
    //@Select("select * from emp")

    //使用xmlsql映射文件配置时 同时根据查询条件进行计数 统计个数
    List<Emp> getList(String name, Short gender, LocalDate begin, LocalDate end);

    void deleteById(List<Integer> ids);

    void insert(Emp emp);
}
