package com.baizhi.zwb.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {
    private String status;
    private String message;
    private Object data;

    //成功
    public CommonResult success(String message,Object data){
       return  new CommonResult("100",message,data);
    }

    //失败
    public CommonResult fail(String message,Object data){
        return  new CommonResult("104",message,data);
    }
}
