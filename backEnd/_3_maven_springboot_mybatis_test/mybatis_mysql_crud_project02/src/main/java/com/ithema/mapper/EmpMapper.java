package com.ithema.mapper;

import com.ithema.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
@Mapper
public interface EmpMapper {
    //1. Mybatis  中的#{}  为参数占位符  用于注解中sql语句中
    // 注意: 如果mapper接口中的方法只有一个返回值 #{} 中的可以任意写 因为只有一个参数
    @Delete("delete from emp where id = #{id}")
    //2. delete 和 insert update都有返回值 返回值为操作影响的记录数  用int类型作为返回值可以获取
    public void delete(Integer id);

    //useGeneratedKeys = true 表示开启主键返回 keyProperty = "id"表示将主键的值封装到用于插入数据的对象的字段中
    @Options(keyProperty = "id", useGeneratedKeys = true)
    //1. 使用对象传参 mybatis会自动或取该对象中的属性名 和占位符中的对应符号自动比对填充
    @Insert("insert into emp(username,name, gender, image, job, entrydate, dept_id, create_time, update_time)" +
            " values(#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    //1. 同样使用emp对象封装数据
//    @Update("update emp set username = #{username},name = #{name},gender = #{gender},image = #{image}," +
//            " job=#{job},entrydate=#{entrydate},dept_id=#{deptId},update_time=#{updateTime} where id = #{id}")
    public void update(Emp emp);

    //    @Select("select * from emp where id = #{id}")
    //    @Select("select username,password,name,gender,id,job,entrydate,dept_id as deptID,create_time createTime,update_time updateTime from emp where id = #{id}")
    //    public Emp select(int id);
    @Results({
            @Result(column = "dept_id", property = "deptId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    @Select("select * from emp where id = #{id}")
    public Emp select(int id);
    //可以使用concat()函数实现字符串拼接来使用占位符concat('%',#{name},'%')
    //@Select("select * from emp where name like '%${name}%' and gender = #{gender} and" +
    //        " entrydate between #{begin} and #{end} " +
    //        " order by update_time desc ")

    //    @Select("select * from emp where name like concat('%',#{name},'%') and gender = #{gender} and" +
    //            " entrydate between #{begin} and #{end} " +
    //            " order by update_time desc ")
    //
    //    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    //Mybatis下使用XML映射文件来操作数据库
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    public void deleteByIds(List<Integer> ids);
}
