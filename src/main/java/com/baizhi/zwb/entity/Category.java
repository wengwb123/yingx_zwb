package com.baizhi.zwb.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name="yingx_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private String id;

    private String name;

    private String levels;

    @Column(name = "parent_id")
    private String parentId;

    private Category cate;

    private List<Category> categoryList;
}