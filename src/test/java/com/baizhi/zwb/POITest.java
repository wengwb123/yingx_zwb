package com.baizhi.zwb;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/*
* 用来测试poi的操作
* */
public class POITest {

    @Test
    //导出一个文件
    public void test1() throws Exception {
        //创建一个excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个工作薄 参数：文件名
        HSSFSheet sheet = workbook.createSheet("我的测试");

        //创建一行 参数：行下标，从0开始
        HSSFRow row = sheet.createRow(0);

        //创建一个单元格 参数：列下标，从0开始
        HSSFCell cell = row.createCell(0);

        //给这个单元格设置内容
        cell.setCellValue("第一行第一列");

        //导出单元格
        workbook.write(new FileOutputStream(new File("e:\\test.xls")));
    }

    //导入poi
    @Test
    public void test2() throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("e:\\test.xls")));
        HSSFSheet sheet = workbook.getSheet("我的测试");
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(0);
        String value = cell.getStringCellValue();
        System.out.println("开始");
        System.out.println(value);
        System.out.println("结束");
    }
}
