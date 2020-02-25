package com.baizhi.zwb.exception;
/*
*管理员登陆异常
*/
public class AdminLoginException extends RuntimeException {
    public AdminLoginException() {
    }

    public AdminLoginException(String message) {
        super(message);
    }
}
