package com.baizhi.zwb.dao;

import com.baizhi.zwb.entity.Video;
import java.util.List;

import com.baizhi.zwb.po.Video2PO;
import com.baizhi.zwb.po.VideoPO;
import tk.mybatis.mapper.common.Mapper;

public interface VideoMapper extends Mapper<Video> {

    //首页视频展示查询所有视频
    public List<VideoPO> selectByReleaseTime();

    //首页查询视频展示
    public List<Video2PO> selectByLikeVideoName(String content);
}