package com.baizhi.zwb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* 用来统计每个月用户注册的数量
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph implements Serializable {
    private String moth;
    private String sex;
    private int num;
}
