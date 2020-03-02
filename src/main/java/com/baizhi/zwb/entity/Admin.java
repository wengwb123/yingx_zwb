package com.baizhi.zwb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import java.util.List;

@Table(name = "yingx_admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    private String id;

    private String username;

    private String password;

    private String salt;

    private List<Role> roleList;

}