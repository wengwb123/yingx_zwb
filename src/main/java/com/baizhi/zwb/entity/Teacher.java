package com.baizhi.zwb.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;

import java.util.List;

public class Teacher {
    @Excel(name = "老师id")
    private String id;
    @Excel(name = "老师姓名")
    private String name;
    @ExcelCollection(name = "学生信息")
    private List<Student> students;
}
