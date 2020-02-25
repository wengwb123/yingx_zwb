package com.baizhi.zwb.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(HttpSession session, Exception e) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        if(e.getClass() == AdminLoginException.class){
            session.setAttribute("adminLoginMess",e.getMessage());
            modelAndView.setViewName("redirect:/login/login.jsp");
        }
        return modelAndView;
    }
}
