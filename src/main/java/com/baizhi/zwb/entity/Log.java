package com.baizhi.zwb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yingx_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    private String id;

    @Column(name = "admin_name")
    private String adminName;

    private Date time;

    private String action;

    private String status;

}