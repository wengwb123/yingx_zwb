package com.baizhi.zwb.service;

import java.util.Map;

public interface LogService {

    //分页查询所有日志信息
    Map<String,Object> queryByPage(Integer page, Integer rows);
}
