package com.ithema.mapper;

import com.ithema.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
//在运行时 会自动生成该接口的实现类对象(代理对象)  并且将该对象交给IOC容器
//等同于实现接口后在使用@Repository注解标识
@Mapper
public interface UserMapper {
    @Select("select * from user")
    public List<User> list();
}
