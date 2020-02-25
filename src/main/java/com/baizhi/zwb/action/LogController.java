package com.baizhi.zwb.action;

import com.baizhi.zwb.service.LogService;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;

    //分页查询所有日志
    @ResponseBody
    @RequestMapping("/queryByPage")
    public Map<String,Object> queryByPage(Integer page,Integer rows){
        Map<String, Object> map = logService.queryByPage(page, rows);
        return map;
    }
}
