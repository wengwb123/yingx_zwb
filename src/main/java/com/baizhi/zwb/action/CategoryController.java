package com.baizhi.zwb.action;

import com.baizhi.zwb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    // 分页查询所有一级类别的分类
    @ResponseBody
    @RequestMapping("/queryByPageAndLevels")
    public Map<String,Object> queryByPageAndLevels(Integer page, Integer rows){
        Map<String, Object> map = categoryService.queryByPageAndLevels(page, rows);
        return map;
    }

    //分页查询某个一级类别下的所有二级类别
    @ResponseBody
    @RequestMapping("/queryByPageAndParentId")
    public Map<String,Object> queryByPageAndParentId(Integer page, Integer rows,String id){
        Map<String, Object> map = categoryService.queryByPageAndParentId(page, rows, id);
        return map;
    }
}
