package com.app.cattask.controller;

import com.app.cattask.pojo.UserToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class HelloController {


    @RequestMapping("/login")
    public String say(Map<String,Object> map){
        return "login";
    }

    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    public String dologin(String username, String password, HttpServletRequest request, HttpServletResponse response){
        Session session = SecurityUtils.getSubject().getSession();
        UserToken userToken = new UserToken( username, password);
        SecurityUtils.getSubject().login(userToken);
        response.addCookie(new Cookie("bobo","123456"));
        return "index";
    }

    @RequestMapping(value = "test")
    @ResponseBody
    public String test1(){
        return "bbbbb";
    }
}
