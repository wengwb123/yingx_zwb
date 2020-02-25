package com.baizhi.zwb.action;

import com.baizhi.zwb.entity.Video;
import com.baizhi.zwb.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    //分页查询所有视频
    @ResponseBody
    @RequestMapping("/queryByPage")
    public Map<String,Object> queryByPage(Integer page,Integer rows){
        Map<String, Object> map = videoService.queryByPage(page, rows);
        return map;
    }

    //执行视频增删改操作
    @ResponseBody
    @RequestMapping("/edit")
    public Object edit(Video video, String oper){
        String id = null;
        if(oper.equals("edit")){
            System.out.println("修改操作");
        }else if(oper.equals("add")){
            id = videoService.add(video);
            return id;
        }else if(oper.equals("del")){
            Map<String, Object> map = videoService.deleteVideo(video);
            return map;
        }
        return null;
    }

    //上传视频
    @ResponseBody
    @RequestMapping("/uploadFile")
    public String uploadFile(MultipartFile path,String id) throws IOException {
        videoService.uploadVideo(path,id);
        return null;
    }
}
