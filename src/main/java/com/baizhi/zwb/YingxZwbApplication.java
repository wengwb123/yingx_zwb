package com.baizhi.zwb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@tk.mybatis.spring.annotation.MapperScan("com.baizhi.zwb.dao")
@MapperScan("com.baizhi.zwb.dao")
@SpringBootApplication
public class YingxZwbApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxZwbApplication.class, args);
    }

}
