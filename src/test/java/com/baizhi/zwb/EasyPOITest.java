package com.baizhi.zwb;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.zwb.entity.Student;
import com.baizhi.zwb.entity.Teacher;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/*
* easypoi的测试
* */
public class EasyPOITest {

    @Test
    //导出测试
    public void test1() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student("1", "a"));
        students.add(new Student("2", "b"));
        students.add(new Student("3", "c"));
        students.add(new Student("4", "d"));
        //配置导出参数  参数：标题,工作表名称
        ExportParams exportParams = new ExportParams("学生信息表", "我的测试");
        //使用导出工具类中方法做导出  参数：导出参数对象,实体类对象,要导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Student.class, students);
        //导出Excel到本地
        workbook.write(new FileOutputStream(new File("e:\\测试.xls")));
    }

    @Test
    //导入测试
    public void test2(){
        //配置导入参数
        ImportParams params = new ImportParams();
        params.setTitleRows(2);  //标题占几行 默认0
        params.setHeadRows(2);  //表头占几行  默认1

        try {
            //获取导入的文件
            FileInputStream inputStream = new FileInputStream(new File("D:\\测试.xls"));
            //导入    参数：读入流,实体类映射
            List<Teacher> teacherList = ExcelImportUtil.importExcel(inputStream,Teacher.class, params);

            //遍历
            /*for (Teacher teacher : teacherList) {
                System.out.println("教师： "+teacher);

                for (Emp e : teacher.getStudents()) {
                    System.out.println("学生: "+e);
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
