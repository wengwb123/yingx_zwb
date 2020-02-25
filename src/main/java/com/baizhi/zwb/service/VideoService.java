package com.baizhi.zwb.service;

import com.baizhi.zwb.entity.Video;
import com.baizhi.zwb.po.Video2PO;
import com.baizhi.zwb.po.VideoPO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface VideoService {
    //分页查询所有视频
    public Map<String,Object> queryByPage(Integer page,Integer rows);

    //添加视频
    public String add(Video video);

    //上传视频到服务器
    public void uploadVideo(MultipartFile file,String id) throws IOException;

    //删除视频
    public Map<String,Object> deleteVideo(Video video);

    //首页视频展示查询所有视频
    public List<VideoPO> queryByReleaseTime();

    //首页查询视频展示
    public List<Video2PO> queryByLikeVideoName(String content);

    //首页上传视频
    public void save(Video video,MultipartFile file)throws Exception;
}
