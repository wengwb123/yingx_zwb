package com.baizhi.zwb.service;

import com.baizhi.zwb.annotation.AddCache;
import com.baizhi.zwb.dao.CategoryMapper;
import com.baizhi.zwb.entity.Category;
import com.baizhi.zwb.entity.CategoryExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Override
    @AddCache
    public Map<String, Object> queryByPageAndLevels(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<String,Object>();
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo("1");
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        int count = categoryMapper.selectCountByExample(example);
        int total = (count % rows == 0) ? (count / rows) : (count / rows + 1);
        map.put("page",page);
        map.put("records",count);
        map.put("total",total);
        map.put("rows",categories);
        return map;
    }

    @Override
    @AddCache
    public Map<String, Object> queryByPageAndParentId(Integer page, Integer rows, String id) {
        Map<String, Object> map = new HashMap<String,Object>();
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        int count = categoryMapper.selectCountByExample(example);
        int total = (count % rows == 0) ? (count / rows) : (count / rows + 1);
        map.put("page",page);
        map.put("records",count);
        map.put("total",total);
        map.put("rows",categories);
        return map;
    }

    @Override
    @AddCache
    public List<Category> queryAllCategory() {
        List<Category> categories = categoryMapper.selectAllCategory();
        return categories;
    }
}
