package com.baizhi.zwb.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Excel(name = "学生ID")
    private String id;
    @Excel(name = "学生姓名")
    private String name;
}
