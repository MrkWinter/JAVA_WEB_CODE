package com.tlias.mapper;

import com.tlias.pojo.Dept;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
@Mapper
public interface DeptMapper {
    //使用注解形式的Sql语句访问数据库
    //自动生成的对象已将JDBC连接数据库的操作 以及获取数据的代码 (其中使用了连接池 更高效) 封装
    //@Mapper注解已将该接口生成的对象交给IOC容器 使用时直接使用@Autowired依赖注入即可
    @Select("select * from dept;")
    List<Dept> list();

    //根据id删除数据
    @Delete("delete from dept where id = #{id};")
    void delete(Integer id);

    //插入部门
    @Insert("insert into dept (name, create_time, update_time) values (#{name},#{createTime},#{updateTime});")
    void insert(Dept dept);

    //修改部门信息
    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id};")
    void update(Dept dept);

    //根据id查询部门信息
    @Select("select * from dept where id = #{id}")
    Dept selectById(Integer id);
}
