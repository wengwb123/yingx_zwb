package com.baizhi.zwb.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yingx_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @Excel(name = "Id")
    private String id;

    @Excel(name = "姓名")
    private String username;

    @Excel(name = "性别")
    private String sex;

    @Excel(name = "电话号码")
    private String phone;

    @Column(name = "pic_img")
    @Excel(name = "头像",type = 2,width = 80 , height = 20)
    private String picImg;

    @Excel(name = "签名")
    private String sign;

    @Excel(name = "状态")
    private String status;

    @Excel(name = "微信号")
    private String wechat;

    @Column(name = "create_date")
    @Excel(name = "创建时间",format = "yyyy年MM月dd日")
    private Date createDate;

    @Excel(name = "分数")
    private Double score;
}