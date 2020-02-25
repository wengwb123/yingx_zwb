package com.baizhi.zwb.dao;

import com.baizhi.zwb.entity.Graph;
import com.baizhi.zwb.entity.User;
import com.baizhi.zwb.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    //统计每个月分用户注册人数
    public List<Graph> selectAllGroupByMothAndSex();
}