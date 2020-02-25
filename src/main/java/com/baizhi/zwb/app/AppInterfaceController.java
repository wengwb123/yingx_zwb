package com.baizhi.zwb.app;

import com.baizhi.zwb.common.CommonResult;
import com.baizhi.zwb.entity.Category;
import com.baizhi.zwb.entity.Video;
import com.baizhi.zwb.po.Video2PO;
import com.baizhi.zwb.po.VideoPO;
import com.baizhi.zwb.service.CategoryService;
import com.baizhi.zwb.service.VideoService;
import com.baizhi.zwb.util.AliyunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
* 对接前端的接口
* */
@RestController
@RequestMapping("app")
public class AppInterfaceController {

    @Autowired
    VideoService videoService;
    @Autowired
    CategoryService categoryService;

    //发送验证码
    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCode(String phone){
        try {
            String message = AliyunUtil.sendPhone(phone);
            if("发送成功".equals(message)){
              return new CommonResult().success("验证码发送成功",phone);
            }else
                return new CommonResult().fail("验证码发送失败",phone);
        } catch (Exception e) {
            return new CommonResult().fail("验证码发送失败",phone);
        }
    }

    //首页展示视频
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(){
        List<VideoPO> data =null;
        try {
            data = videoService.queryByReleaseTime();
            return new CommonResult().success("查询成功",data);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().fail("查询失败",data);
        }
    }

    //首页搜索视频
    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content){
        List<Video2PO> data = null;
        try {
            data = videoService.queryByLikeVideoName(content);
            return new CommonResult().success("查询成功",data);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().fail("查询失败",data);
        }
    }

    //首页用户发布视频
    /*
    *由于一系列问题导致属性名对不上，只能采取这样的方法
     */
    @RequestMapping("save")
    public CommonResult save(Video video, String description,String videoTitle, MultipartFile videoFile){
        Object data = null;
        try {
            video.setBrief(description);
            video.setTitle(videoTitle);
            System.out.println("开始");
            System.out.println(videoFile.getOriginalFilename());
            System.out.println(video);
            System.out.println("结束");
            videoService.save(video,videoFile);
            return new CommonResult().success("添加成功",data);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().success("添加失败",data);
        }
    }

    //分页查询数据
    /*
    * 在测试时没出现结果的原因时实体类属性名为name，需要的时cateName
    * */
    @RequestMapping("queryAllCategory")
    public CommonResult queryAllCategory(){
        List<Category> data = null;
        try {
            data = categoryService.queryAllCategory();
            return new CommonResult().success("查询成功",data);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().fail("查询失败",data);
        }
    }

    //
}
