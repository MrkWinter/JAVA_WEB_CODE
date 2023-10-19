package com.ithema.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MrkWinter
 * @version 1.0
 */
//存放每一条数据的实体类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Short age;
    private Short gender;
    private String phone;

}
