package com.baizhi.zwb.action;

import com.baizhi.zwb.util.CreateValidateCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/code")
public class CodeController {

    //生成验证码
    @RequestMapping("/getCode")
    public String getCode(HttpSession session, HttpServletResponse response) throws Exception{
        // 使用方法（在Servlet中添加下面代码）
        CreateValidateCodeUtil vCode = new CreateValidateCodeUtil(100, 30, 4, 10);
         session.setAttribute("code", vCode.getCode()); //保存在Session作用域
         vCode.write(response.getOutputStream());
         return null;
    }
}
