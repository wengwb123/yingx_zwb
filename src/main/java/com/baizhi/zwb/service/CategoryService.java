package com.baizhi.zwb.service;

import com.baizhi.zwb.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    //分页查询所有一级分类
    public Map<String, Object> queryByPageAndLevels(Integer page, Integer rows);

    //分页查询某个一级类别下的所有二级类别
    public Map<String, Object> queryByPageAndParentId(Integer page, Integer rows,String id);

    //查询所有的分类
    public List<Category> queryAllCategory();
}
