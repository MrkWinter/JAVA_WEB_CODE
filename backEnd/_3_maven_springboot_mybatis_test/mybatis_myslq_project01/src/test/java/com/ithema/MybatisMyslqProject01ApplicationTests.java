package com.ithema;

import com.ithema.mapper.UserMapper;
import com.ithema.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

// @SpringBoot + @Test注解
// @SpringBoot又包含了其他注解
@SpringBootTest
class MybatisMyslqProject01ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testUserMapper() {
        List<User> list = userMapper.list();
        list.stream().forEach(user -> {
            System.out.println(user);
        });
    }

}
