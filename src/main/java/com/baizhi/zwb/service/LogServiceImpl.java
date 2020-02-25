package com.baizhi.zwb.service;

import com.baizhi.zwb.dao.LogMapper;
import com.baizhi.zwb.entity.Category;
import com.baizhi.zwb.entity.CategoryExample;
import com.baizhi.zwb.entity.Log;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    LogMapper logMapper;

    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<String,Object>();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Log> logs = logMapper.selectByRowBounds(new Log(), rowBounds);
        int count = logMapper.selectCount(new Log());
        int total = (count % rows == 0) ? (count / rows) : (count / rows + 1);
        map.put("page",page);
        map.put("records",count);
        map.put("total",total);
        map.put("rows",logs);
        return map;
    }
}
