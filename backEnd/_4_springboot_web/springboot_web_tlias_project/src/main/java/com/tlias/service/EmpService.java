package com.tlias.service;

import com.tlias.pojo.Emp;
import com.tlias.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * @author MrkWinter
 * @version 1.0
 */
public interface EmpService {
    PageBean query(String name, Short gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize);

    void deleteById(List<Integer> ids);

    void save(Emp emp);
}
