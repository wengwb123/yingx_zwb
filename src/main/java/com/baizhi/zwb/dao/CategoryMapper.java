package com.baizhi.zwb.dao;

import com.baizhi.zwb.entity.Category;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {
    //查询所有的分类
    public List<Category> selectAllCategory();
}