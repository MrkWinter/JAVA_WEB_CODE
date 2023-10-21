package com.tlias.service;

import com.tlias.pojo.Dept;

import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
public interface DeptService {
    List<Dept> list();

    void delete(Integer id);

    void add(Dept dept);

    void update(Dept dept);

    Dept selectById(Integer id);
}
