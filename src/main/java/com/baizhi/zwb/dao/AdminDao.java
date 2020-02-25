package com.baizhi.zwb.dao;

import com.baizhi.zwb.entity.Admin;

public interface AdminDao {
    Admin selectByUsername(String username);
}
