package com.baizhi.zwb.service;

import com.baizhi.zwb.annotation.AddLog;
import com.baizhi.zwb.dao.VideoMapper;
import com.baizhi.zwb.entity.Video;
import com.baizhi.zwb.entity.VideoExample;
import com.baizhi.zwb.po.Video2PO;
import com.baizhi.zwb.po.VideoPO;
import com.baizhi.zwb.util.InterceptVideoPhotoUtil;
import com.baizhi.zwb.util.QiuNiuUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    VideoMapper videoMapper;
    @Resource
    HttpSession session;

    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        //分页查询数据
        List<Video> videos = videoMapper.selectByExampleAndRowBounds(new VideoExample(), new RowBounds((page - 1) * rows, rows));
        //查询视频的总条数
        int count = videoMapper.selectCount(new Video());
        int total = (count % rows == 0) ? (count / rows) : (count / rows + 1);
        map.put("page",page);
        map.put("records",count);
        map.put("total",total);
        map.put("rows",videos);
        return map;
    }

    @Override
    @AddLog(value = "添加视频")
    public String add(Video video) {
        String id = UUID.randomUUID().toString();
        video.setId(id);
        video.setUploadTime(new Date());
        videoMapper.insertSelective(video);
        return id;
    }

    @Override
    @AddLog("上传视频和封面")
    public void uploadVideo(MultipartFile path, String id) throws IOException {
        //设置视频文件名
        String fileName = new Date().getTime()+path.getOriginalFilename();

        //上传视频到七牛云
        QiuNiuUtil.upload(path,fileName);

        //拼接远程视频路径
        String videoFilePath = "http://q5uliug95.bkt.clouddn.com/"+fileName;

        //根据相对路径获取绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/cover");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //设置封面名
        String[] split = fileName.split("\\.");
        String coverName = split[0]+".jpg";
        String coverPath = realPath+"/"+coverName;

        //截取视频作为封面
        try {
            InterceptVideoPhotoUtil.fetchFrame(videoFilePath,coverPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //上传封面到七牛云
        QiuNiuUtil.upload(coverPath,coverName);

        //修改存储在数据库中的path信息
        Video video = new Video();
        video.setId(id);
        video.setCover("http://q5uliug95.bkt.clouddn.com/"+coverName);
        video.setPath("http://q5uliug95.bkt.clouddn.com/"+fileName);
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    @AddLog("删除视频")
    public Map<String, Object> deleteVideo(Video video) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //先查询该数据
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(video.getId());
            Video vi = videoMapper.selectOneByExample(example);

            //删除该数据
            videoMapper.deleteByPrimaryKey(video);

            //删除服务器上的封面和视频
            String cover = vi.getCover();
            String path = vi.getPath();
            String coverName = cover.split("/")[3];
            String pathName = path.split("/")[3];
            QiuNiuUtil.delete(coverName);
            QiuNiuUtil.delete(pathName);
            map.put("status","200");
            map.put("message","删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status","400");
            map.put("message","删除失败");
        }
        return map;
    }

    @Override
    public List<VideoPO> queryByReleaseTime() {
        List<VideoPO> data = videoMapper.selectByReleaseTime();
        return data;
    }

    @Override
    public List<Video2PO> queryByLikeVideoName(String content) {
        List<Video2PO> data = videoMapper.selectByLikeVideoName("%"+content+"%");
        return data;
    }

    @Override
    public void save(Video video,MultipartFile videoFile)throws Exception {
        //设置视频文件名
        String fileName = new Date().getTime()+videoFile.getOriginalFilename();

        //上传视频到七牛云
        QiuNiuUtil.upload(videoFile,fileName);

        //拼接远程视频路径
        String videoFilePath = "http://q5uliug95.bkt.clouddn.com/"+fileName;

        //根据相对路径获取绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/cover");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //设置封面名
        String[] split = fileName.split("\\.");
        String coverName = split[0]+".jpg";
        String coverPath = realPath+"/"+coverName;

        //截取视频作为封面
        try {
            InterceptVideoPhotoUtil.fetchFrame(videoFilePath,coverPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //上传封面到七牛云
        QiuNiuUtil.upload(coverPath,coverName);

        //修改存储在数据库中的path信息
        video.setId(UUID.randomUUID().toString());
        video.setCover("http://q5uliug95.bkt.clouddn.com/"+coverName);
        video.setPath("http://q5uliug95.bkt.clouddn.com/"+fileName);
        videoMapper.insertSelective(video);
    }
}
