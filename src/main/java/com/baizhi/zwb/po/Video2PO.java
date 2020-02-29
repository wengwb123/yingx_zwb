package com.baizhi.zwb.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/*
*首页收索视频接口使用的对象
* 再次封装video，方便查询结果，在mapper中不需要再写resultMap
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video2PO implements Serializable {

    private String id;
    private String videoTitle;
    private String description;
    private String cover;
    private String path;
    private Date uploadTime;
    String likeCount;
    String cateName;
    String categoryId;
    String userId;
    String userName;
}
